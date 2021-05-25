package ru.inversion.rtgs.payload.request

/**
 * @author Dmitry Hvastunov
 * @created 24.05.2021
 * @project rtgs
 */

data class TrnRequest(
        val date: String,
        val user_id: Long
){
}