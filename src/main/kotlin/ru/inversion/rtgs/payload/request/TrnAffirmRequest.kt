package ru.inversion.rtgs.payload.request

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */

data class TrnAffirmRequest (
        val idList: List<Long>? = null
)