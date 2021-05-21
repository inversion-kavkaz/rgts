package ru.inversion.rtgs.dto

import org.springframework.security.core.GrantedAuthority
import ru.inversion.rtgs.entity.enums.ERole
import java.time.LocalDateTime

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

data class UserDTO(
        val id: Long?,
        val bank_id: Long?,
        val EName: String?,
        val login: String?,
        val roles: MutableSet<ERole> = mutableSetOf(),
        var createdDate: LocalDateTime? = null
)