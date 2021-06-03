package ru.inversion.rtgs.payload.response

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */

data class TrnAffirmResponse (
        val affirmResult: String? = "SUCCESS",
        val affirmMessage: String? = "",
        val itrnnum: Long? = null
)