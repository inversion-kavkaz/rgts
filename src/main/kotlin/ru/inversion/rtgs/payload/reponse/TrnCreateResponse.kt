package ru.inversion.rtgs.payload.reponse

import ru.inversion.rtgs.entity.RtgsTrn

data class TrnCreateResponse (
        val trn: RtgsTrn?,
        val responseResult: String?
)
