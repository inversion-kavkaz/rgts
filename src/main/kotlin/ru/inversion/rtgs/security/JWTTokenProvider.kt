package ru.inversion.rtgs.security

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import ru.inversion.rtgs.entity.RtgsUser
import java.util.*

@Component
class JWTTokenProvider {
    fun generateToken(authentication: Authentication): String {
        val (id, _, EName, login) = authentication.principal as RtgsUser
        val now = Date(System.currentTimeMillis())
        val expiryDate = Date(now.time + SecurityConstants.EXPIRATION_TIME)
        val userId = java.lang.Long.toString(id!!)
        val claimsMap: MutableMap<String, Any?> = HashMap()
        claimsMap["id"] = userId
        claimsMap["login"] = login
        claimsMap["name"] = EName
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact()
    }

    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token)
            true
        } catch (ex: SignatureException) {
            LOG.error(ex.message)
            false
        } catch (ex: MalformedJwtException) {
            LOG.error(ex.message)
            false
        } catch (ex: ExpiredJwtException) {
            LOG.error(ex.message)
            false
        } catch (ex: UnsupportedJwtException) {
            LOG.error(ex.message)
            false
        } catch (ex: IllegalArgumentException) {
            LOG.error(ex.message)
            false
        }
    }

    fun getUserIdFromToken(token: String?): Long {
        val claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .body
        val id = claims["id"] as String?
        return id!!.toLong()
    }

    companion object {
        val LOG = LoggerFactory.getLogger(JWTTokenProvider::class.java)
    }
}
