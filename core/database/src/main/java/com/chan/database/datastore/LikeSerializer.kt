package com.chan.database.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.chan.like.proto.Like
import java.io.InputStream
import java.io.OutputStream

object LikeSerializer : Serializer<Like> {
    override val defaultValue: Like = Like.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Like {
        try {
            return Like.parseFrom(input)
        } catch (exception: com.google.protobuf.InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Like, output: OutputStream) = t.writeTo(output)
}