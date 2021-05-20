package ru.inversion.rtgs.payload.reponse

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
data class JWTTokenSuccessResponse(
    val success: Boolean = false,
    val token: String? = null
)
