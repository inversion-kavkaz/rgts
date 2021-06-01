package ru.inversion.rtgs.payload.reponse

import java.math.BigDecimal

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */
data class BalanceResponse(
        val real_balance: BigDecimal?,
        val planned_balance: BigDecimal?,
        val payment_position: BigDecimal?

)
