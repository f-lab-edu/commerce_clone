package com.chan.auth.data

import android.content.SharedPreferences
import com.chan.auth.domain.AuthRepository
import com.chan.auth.domain.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
) : AuthRepository {

    private val _sessionFlow = MutableStateFlow<UserSession?>(null)
    override fun getSessionFlow(): Flow<UserSession?> = _sessionFlow.asStateFlow()

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_TOKEN = "token"
    }

    init {
        val userId = prefs.getString(KEY_USER_ID, null)
        val token = prefs.getString(KEY_TOKEN, null)
        if (userId != null && token != null) {
            _sessionFlow.value = UserSession(userId, token)
        }
    }

    override suspend fun login(userId: String, token: String) {
        prefs.edit()
            .putString(KEY_USER_ID, userId)
            .putString(KEY_TOKEN, token)
            .apply()

        _sessionFlow.value = UserSession(userId, token)
    }

    override suspend fun logout() {
        prefs.edit().remove(KEY_USER_ID).remove(KEY_TOKEN).apply()

        _sessionFlow.value = null
    }

    override fun getCurrentUserId(): String? {
        return _sessionFlow.value?.userId ?: prefs.getString(KEY_USER_ID, null)
    }
}