package ru.inversion.rtgs.security

object SecurityConstants {
    const val SIGN_UP_URLS = "/api/auth/*"
    const val SECRET = "SecretKeyGenJWT"
    const val TOKEN_PREFIX = "Inversion "
    const val HEADER_STRING = "Authorization"
    const val CONTENT_TYPE = "application/json"
    const val EXPIRATION_TIME: Long = 600000 //10min
}
