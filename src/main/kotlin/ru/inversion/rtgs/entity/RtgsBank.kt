package ru.inversion.rtgs.entity

import java.math.BigInteger
import javax.persistence.*

/**
 * @author Dmitry Hvastunov
 * @created 20.05.2021
 * @project rtgs
 */

@Entity
data class RtgsBank(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(nullable = false,unique = true)
        val bik: String? = "",
        val bankAdress: String? = "",
        @Column(nullable = false)
        val bankName: String? = "",
        @Column(nullable = false,unique = true,length = 20)
        val corrAcc: String? = "",


        @OneToMany(mappedBy = "bank_id", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        val rtgsUsers: Collection<RtgsUser>? = null
){
        constructor() : this(null,"","",null, null) {}
}
