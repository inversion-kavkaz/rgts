package ru.inversion.rtgs.payload.request

import lombok.Data
import javax.validation.constraints.NotEmpty

data class LoginRequest (
    val login: @NotEmpty(message = "Login cannot be empty") String? = null,
    val password: @NotEmpty(message = "Password cannot be empty") String? = null
)
