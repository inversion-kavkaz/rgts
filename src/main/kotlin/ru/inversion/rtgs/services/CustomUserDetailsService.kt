package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.inversion.rtgs.entity.RtgsUser
import ru.inversion.rtgs.entity.enums.ERole
import ru.inversion.rtgs.repository.UserRepository
import java.util.stream.Collectors

@Service
class CustomUserDetailsService @Autowired constructor(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(login: String): UserDetails {
        val user = userRepository.findRtgsUserByLogin(login)
                .orElseThrow { UsernameNotFoundException("User not found with login: $login") }
        return build(user)
    }

    fun loadUserById(id: Long): RtgsUser {
        return userRepository.findById(id).orElse(null)
    }

    companion object {
        fun build(user: RtgsUser): RtgsUser {
            val authorities: List<GrantedAuthority?> = user.roles.stream()
                    .map { role: ERole -> SimpleGrantedAuthority(role.name) }
                    .collect(Collectors.toList())
            return RtgsUser(
                    user.id,
                    user.bank_id,
                    user.EName,
                    user.login,
                    user.password!!,
                    authorities)
        }
    }

}
