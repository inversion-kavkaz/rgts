package ru.inversion.rtgs.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.persistence.Column

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */


data class TrnDTO (
        val id: Long?,
        val edNo: Long?,
        val edAuthor: String?,
        val edReceiver: String?,
        val transKind: String?,
        val priority: Long? = 1,
        val sum: Double? = null,
        val payerPersonalAcc: String?,
        val payerINN: String?,
        val payerCorrespAcc: String?,
        val payeePersonalAcc: String?,
        val payeeINN: String?,
        val payeeCorrespAcc: String?,
        val userId: Long?,
        val purpose: String?,
        var edDate: LocalDateTime? = null
)
