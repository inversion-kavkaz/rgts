package ru.inversion.rtgs.facade

import org.springframework.stereotype.Component
import ru.inversion.rtgs.dto.TrnDTO
import ru.inversion.rtgs.entity.RtgsTrn

@Component
class TrnFacade {
    fun trnToTrnFacade(trn: RtgsTrn): TrnDTO = TrnDTO(
            trn.itrnnum,
            trn.itrnanum,
            trn.edNo,
            trn.edAuthor,
            trn.edReceiver,
            trn.transKind,
            trn.priority,
            trn.sum,
            trn.payerPersonalAcc,
            trn.payerINN,
            trn.payerCorrespAcc,
            trn.payeePersonalAcc,
            trn.payeeINN,
            trn.payeeCorrespAcc,
            trn.login,
            trn.purpose,
            trn.edDate,
            trn.payerName,
            trn.payeeName,
            trn.status,
            trn.currency
    )
}
