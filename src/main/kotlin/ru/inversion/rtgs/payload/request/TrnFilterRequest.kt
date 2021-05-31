package ru.inversion.rtgs.payload.request

/**
 * @author Dmitry Hvastunov
 * @created 31.05.2021
 * @project rtgs
 */
data class TrnFilterRequest(
        val filter: Filter,
        val login: String
){
}