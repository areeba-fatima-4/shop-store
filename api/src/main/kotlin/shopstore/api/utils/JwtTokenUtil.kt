package shopstore.api.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import shopstore.api.models.JwtUser
import java.util.Date
import java.util.function.Function


@Component
data class JwtTokenUtil(
    @Value("\${jwt.token.validity}")
    val jwtTokenValidity: String,
    @Value("\${jwt.secret}")
    val jwtSecret: String,
) : java.io.Serializable {

    fun getEmailFromToken(token : String) =
        getClaimFromToken(token) { obj: Claims -> obj.subject }

    fun getExpirationDateFromToken(token : String) =
        getClaimFromToken(token) { obj: Claims -> obj.expiration }

    fun <T> getClaimFromToken(
        token: String,
        resolver: Function<Claims, T>): T =
        getAllClaimsFromToken(token)
            .let { resolver.apply(it) }

    fun getAllClaimsFromToken(token: String) =
        Jwts
            .parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body

    fun isTokenExpired(token: String) =
        getExpirationDateFromToken(token).before(Date())

    fun validateToken(token: String, jwtUserDetails: JwtUser) =
        !isTokenExpired(token) && getEmailFromToken(token).equals(jwtUserDetails.userName)

    fun generateToken(jwtUserDetails: JwtUser) =
        generateToken(generateClaims() as Claims, jwtUserDetails.email)


    fun generateToken(claims: Claims, subject: String): String =
         Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + ((jwtTokenValidity.toLongOrNull()?:5) * 60 *10000)) )
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()

    fun generateClaims() = mutableMapOf<String, Any>()

}
