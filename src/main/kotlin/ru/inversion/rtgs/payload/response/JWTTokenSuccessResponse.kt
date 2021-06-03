package ru.inversion.rtgs.payload.response

import lombok.AllArgsConstructor
import lombok.Data
import ru.inversion.rtgs.dto.BankDTO
import ru.inversion.rtgs.dto.UserDTO

@Data
@AllArgsConstructor
data class JWTTokenSuccessResponse(
    val success: Boolean = false,
    val token: String? = null,
    val user: UserDTO,
    val bank: BankDTO?
)
