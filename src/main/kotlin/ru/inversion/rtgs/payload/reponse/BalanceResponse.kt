package ru.inversion.rtgs.payload.reponse

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */
data class BalanceResponse(
        val real_balance: String?,
        val planned_balance: String?,
        val payment_position: String?

)