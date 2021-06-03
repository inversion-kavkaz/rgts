package ru.inversion.rtgs.payload.response

import ru.inversion.rtgs.entity.RtgsTrn

/**
 * @author Dmitry Hvastunov
 * @created 03.06.2021
 * @project rtgs
 */

data class TrnFilterResponse(
        val trnList: List<RtgsTrn?>,
        val loadLength: Long?
)