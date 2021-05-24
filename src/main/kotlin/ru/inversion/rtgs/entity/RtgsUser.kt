package ru.inversion.rtgs.entity

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.inversion.rtgs.entity.enums.ERole
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 * @author Dmitry Hvastunov
 * @created 20.05.2021
 * @project rtgs
 */

@Entity
@Data
@NoArgsConstructor
data class RtgsUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @Column(nullable = false)
        val bank_id: Long? = null,
        @Column(nullable = false)
        var EName: String? = "",
        @Column(nullable = false,unique = true)
        val login: String? = null,
        @Column(nullable = false,length = 3000)
        private var password: String,
        @Transient
        private var authorities: List<GrantedAuthority?>
) : UserDetails {

    constructor() : this(null,null,"","","", listOf()) {}

    @ElementCollection(targetClass = ERole::class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    var roles: MutableSet<ERole> = mutableSetOf()

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    var createdDate: LocalDateTime? = null

    override fun getAuthorities(): List<out GrantedAuthority>? {
        return this.authorities as List<out GrantedAuthority>
    }

    @PrePersist
    fun onCreate() {
        this.createdDate = LocalDateTime.now()
    }

    fun setPassword(pass: String?){
        if (pass != null) {
            this.password = pass
        }
    }
    /**
     * SECURITY
     */

    override fun isEnabled(): kotlin.Boolean = true

    override fun getUsername(): String? = login

    override fun isCredentialsNonExpired(): kotlin.Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun getPassword(): String? = password


}
