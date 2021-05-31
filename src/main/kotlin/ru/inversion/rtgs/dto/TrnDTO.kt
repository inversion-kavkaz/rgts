package ru.inversion.rtgs.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */


data class TrnDTO (
        val itrnnum: Long?,
        val itrnanum: Long?,
        val edNo: Long?,
        val edAuthor: String?,
        val edReceiver: String?,
        val transKind: Long?,
        val priority: Long? = 1,
        val sum: Long? = null,
        val payerPersonalAcc: String?,
        val payerINN: String?,
        val payerCorrespAcc: String?,
        val payeePersonalAcc: String?,
        val payeeINN: String?,
        val payeeCorrespAcc: String?,
        val login: String?,
        val purpose: String?,
        var edDate: Date? = null,
        val payerName: String? = null,
        val payeeName: String? = null,
        val status : String? = null,
        val currency: String?
        )
