package ru.inversion.rtgs.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import ru.inversion.rtgs.services.CustomUserDetailsService
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JWTAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private val jwtTokenProvider: JWTTokenProvider? = null
    @Autowired
    private val customUserDetailsService: CustomUserDetailsService? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        try {
            val jwt = getJWTFromRequest(httpServletRequest)
            if (StringUtils.hasText(jwt) && jwtTokenProvider!!.validateToken(jwt)) {
                val userId = jwtTokenProvider.getUserIdFromToken(jwt)
                val userDetails = customUserDetailsService!!.loadUserById(userId)
                val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, emptyList())
                authentication.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: Exception) {
            LOG.error("Could not set user authentication")
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearToken = request.getHeader(SecurityConstants.HEADER_STRING)
        return if (StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            bearToken.split(" ").toTypedArray()[1]
        } else null
    }

    companion object {
        val LOG = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)
    }
}
