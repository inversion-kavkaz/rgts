package ru.inversion.rtgs.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.inversion.rtgs.dto.UserDTO
import ru.inversion.rtgs.facade.UserFacade
import ru.inversion.rtgs.repository.UserRepository
import java.util.stream.Collectors

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

@RestController
@CrossOrigin
@RequestMapping("/user")
@PreAuthorize("permitAll()")
class UserController @Autowired constructor(
        private val userRepository: UserRepository,
        private val userFacade: UserFacade) {

    @GetMapping("/all")
    fun GetAllUser(): ResponseEntity<List<UserDTO>> {
        val userDTOList: List<UserDTO> = userRepository.findAll()
                ?.stream()
                ?.map { p -> userFacade?.userTOUserDTO(p) }
                ?.collect(Collectors.toList()) as List<UserDTO>? ?: emptyList()
        return ResponseEntity(userDTOList, HttpStatus.OK)

    }

}