package ru.inversion.rtgs.entity

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
        val id: Long,
        @Column(nullable = false,unique = true)
        val bik: String,
        val bankAdress: String?,
        @Column(nullable = false)
        val bankName: String,

        @OneToMany(mappedBy = "bank_id", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        val rtgsUsers: Collection<RtgsUser>?


)
