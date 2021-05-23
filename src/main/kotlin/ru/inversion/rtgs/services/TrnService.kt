package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.inversion.rtgs.entity.RtgsTrn
import ru.inversion.rtgs.repository.TrnRepository

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

@Service
class TrnService @Autowired constructor(private val trnRepository: TrnRepository) {


    fun create(trn : RtgsTrn) : RtgsTrn {
        return trnRepository.save(trn)
    }

    fun update(trn : RtgsTrn) : RtgsTrn { /**Так не получится*/
        trnRepository.delete(trn)
        return trnRepository.save(trn)
    }

    fun delete(trnId : Long) = trnRepository.deleteById(trnId)

}
