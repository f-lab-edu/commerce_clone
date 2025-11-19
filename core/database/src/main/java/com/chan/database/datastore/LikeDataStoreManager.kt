package com.chan.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.chan.like.proto.Like
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.util.concurrent.ConcurrentHashMap

object LikeDataStoreManager {

    private val storeCache = ConcurrentHashMap<String, DataStore<Like>>()
    private val scopeCache = ConcurrentHashMap<String, CoroutineScope>()

    fun getDataStore(context: Context, userId: String): DataStore<Like> {

        return storeCache.getOrPut(userId) {
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            scopeCache[userId] = scope

            DataStoreFactory.create(
                serializer = LikeSerializer,
                produceFile = { context.dataStoreFile("like_$userId.pb") },
                scope = scope
            )
        }
    }

    fun clearAll() {
        scopeCache.values.forEach { it.cancel() }
        scopeCache.clear()
        storeCache.clear()
    }
}