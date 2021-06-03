package ru.inversion.rtgs.payload.response

data class InvalidLoginResponse (
    val login: String = "Invalid Username",
    val password: String = "Invalid Password"
)
