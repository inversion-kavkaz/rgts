package ru.inversion.rtgs.facade

import org.springframework.stereotype.Component
import ru.inversion.rtgs.dto.UserDTO
import ru.inversion.rtgs.entity.RtgsUser

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */
@Component
class UserFacade {
    fun userTOUserDTO(user: RtgsUser): UserDTO = UserDTO(user.id,user.bank_id,user.EName,user.login, user.roles,user.createdDate)
}