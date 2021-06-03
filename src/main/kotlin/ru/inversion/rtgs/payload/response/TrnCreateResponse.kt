package ru.inversion.rtgs.payload.response

import ru.inversion.rtgs.entity.RtgsTrn

data class TrnCreateResponse (
        val trn: RtgsTrn?,
        val responseResult: String?
)
