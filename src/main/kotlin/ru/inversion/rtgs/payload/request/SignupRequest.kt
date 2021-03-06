package ru.inversion.rtgs.payload.request

import lombok.NonNull
import ru.inversion.rtgs.entity.enums.ERole
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class SignupRequest (
        val bank_id: @NonNull() Long? = null,
        val eName: @NotBlank(message = "User name is required") String? = null,
        val login: @NotEmpty(message = "Please enter your login") String? = null,
        val password: @NotEmpty(message = "Password is required") String? = null,
        val roles: @NotEmpty(message = "Roles is required") MutableSet<ERole>? = null
        )
