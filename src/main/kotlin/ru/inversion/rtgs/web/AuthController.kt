package ru.inversion.rtgs.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.inversion.rtgs.payload.reponse.JWTTokenSuccessResponse
import ru.inversion.rtgs.payload.reponse.MessageResponse
import ru.inversion.rtgs.payload.request.LoginRequest
import ru.inversion.rtgs.payload.request.SignupRequest
import ru.inversion.rtgs.security.JWTTokenProvider
import ru.inversion.rtgs.security.SecurityConstants
import ru.inversion.rtgs.services.UserService
import ru.inversion.rtgs.validations.ResponseErrorValidation
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
class AuthController {
    @Autowired
    private val jwtTokenProvider: JWTTokenProvider? = null
    @Autowired
    private val authenticationManager: AuthenticationManager? = null
    @Autowired
    private val responseErrorValidation: ResponseErrorValidation? = null
    @Autowired
    private val userService: UserService? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?, bindingResult: BindingResult?): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        val authentication = authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(
                loginRequest!!.login,
                loginRequest.password
        ))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider!!.generateToken(authentication)
        return ResponseEntity.ok(JWTTokenSuccessResponse(true, jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signupRequest: @Valid SignupRequest?, bindingResult: BindingResult?): ResponseEntity<Any>? {
        val errors: ResponseEntity<Any>? = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        if (userService != null) {
            signupRequest?.let { userService.createUser(it) }
        }
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}
