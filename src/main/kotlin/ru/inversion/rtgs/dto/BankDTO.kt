package ru.inversion.rtgs.dto

import java.math.BigInteger

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

data class BankDTO(
        val id: Long?,
        val bik: String?,
        val bankAdress: String? = "",
        val bankName : String? = "",
        val corrAcc : String? = null
)