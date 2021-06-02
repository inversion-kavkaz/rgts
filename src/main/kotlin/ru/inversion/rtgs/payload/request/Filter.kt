package ru.inversion.rtgs.payload.request

import java.time.LocalDate
import java.util.*

data class Filter (
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val login: String?,
    val sum: Long?,
    val payerPersonalAcc: String?,
    val payerCorrespAcc: String?,
    val payeePersonalAcc: String?,
    val payeeCorrespAcc: String?,
    val purpose: String?,
    val payerName: String?,
    val payeeName: String?,
    val currency : String?,
    val status : Long?
)
