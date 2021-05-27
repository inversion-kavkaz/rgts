package ru.inversion.rtgs.payload.request

/**
 * @author Dmitry Hvastunov
 * @created 27.05.2021
 * @project rtgs
 */

data class DeleteRequest(
        val idList: List<Long>? = null
)