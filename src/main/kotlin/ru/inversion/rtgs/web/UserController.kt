package ru.inversion.rtgs.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.inversion.rtgs.dto.UserDTO
import ru.inversion.rtgs.facade.UserFacade
import ru.inversion.rtgs.repository.UserRepository
import ru.inversion.rtgs.services.UserService
import ru.inversion.rtgs.validations.ResponseErrorValidation
import java.security.Principal
import java.util.stream.Collectors
import javax.validation.Valid

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
        private val userFacade: UserFacade,
        private val responseErrorValidation: ResponseErrorValidation,
        private val userService: UserService
) {

    @GetMapping("/all/{id}")
    fun GetAllUser(@PathVariable id: Long): ResponseEntity<List<UserDTO>> {
        val userDTOList: List<UserDTO> = userRepository.findAllByBank_idEquals(id)
                ?.stream()
                ?.map { p -> userFacade?.userTOUserDTO(p) }
                ?.collect(Collectors.toList()) as List<UserDTO>? ?: emptyList()
        return ResponseEntity(userDTOList, HttpStatus.OK)
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody user: @Valid UserDTO, bindingResult: BindingResult, principal: Principal): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors

        return ResponseEntity(userFacade.userTOUserDTO(userService.updateUser(user,principal)), HttpStatus.OK)
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Any> {
        userRepository.deleteById(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}
