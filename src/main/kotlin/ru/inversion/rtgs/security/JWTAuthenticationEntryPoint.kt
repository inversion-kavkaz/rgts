package ru.inversion.rtgs.security

import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import ru.inversion.rtgs.payload.reponse.InvalidLoginResponse
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

@Component
class JWTAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: AuthenticationException) {
        val loginResponse = InvalidLoginResponse()
        val jsonLoginResponse = Gson().toJson(loginResponse)
        httpServletResponse.contentType = SecurityConstants.CONTENT_TYPE
        httpServletResponse.status = HttpStatus.UNAUTHORIZED.value()
        httpServletResponse.writer.println(jsonLoginResponse)
    }
}
