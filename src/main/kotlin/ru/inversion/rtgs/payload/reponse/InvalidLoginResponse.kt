package ru.inversion.rtgs.payload.reponse

data class InvalidLoginResponse (
    val login: String = "Invalid Username",
    val password: String = "Invalid Password"
)
