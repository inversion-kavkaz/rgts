package ru.inversion.rtgs.services

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.inversion.rtgs.dto.UserDTO
import ru.inversion.rtgs.entity.RtgsUser
import ru.inversion.rtgs.entity.enums.ERole
import ru.inversion.rtgs.exceptions.UserExistException
import ru.inversion.rtgs.facade.UserFacade
import ru.inversion.rtgs.payload.request.SignupRequest
import ru.inversion.rtgs.repository.UserRepository
import java.security.Principal

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository,
                                         private val passwordEncoder: BCryptPasswordEncoder,
                                         private val userFacade: UserFacade
                                         ) {

    fun getCurrentUser(principal: Principal): RtgsUser {
        return getUserByPrincipal(principal)
    }

    private fun getUserByPrincipal(principal: Principal): RtgsUser {
        val userlogin = principal.name
        return userRepository.findRtgsUserByLogin(userlogin)
                .orElseThrow { UsernameNotFoundException("User not found with login $userlogin") }
    }

    fun getUserByLogin(login: String): RtgsUser {
        return userRepository.findRtgsUserByLogin(login)
                .orElseThrow { UsernameNotFoundException("User not found with login $login")}
    }

    fun getUserById(id: Long): RtgsUser {
        return userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found") }
    }

    fun createUser(userIn: SignupRequest): RtgsUser? {
        val user = RtgsUser(null,
                userIn.bank_id,
                userIn.eName,
                userIn.login,
                passwordEncoder.encode(userIn.password),
                listOf()
                )
        user.roles = userIn.roles!!
        //user.roles.add(ERole.ROLE_USER)

        return try {
            LOG.info("Saving User {} ${userIn.login}" )
            userRepository.save(user)
        } catch (e: Exception) {
            LOG.error("Error during registration. {}", e.message)
            throw UserExistException("The user ${user.login} already exist. Please change new login")
        }
    }

    fun updateUser(userDTO: UserDTO,principal: Principal) : RtgsUser? {
        val bdUser : RtgsUser? = userDTO.id?.let { getUserById(it) }
        bdUser?.EName = userDTO.EName
        bdUser?.roles = userDTO.roles
        if (!userDTO.password.isNullOrEmpty())
            bdUser?.password = passwordEncoder.encode(userDTO.password)
        return bdUser?.let { userRepository.save(it) }
    }

    companion object {
        val LOG = LoggerFactory.getLogger(UserService::class.java)
    }

}
