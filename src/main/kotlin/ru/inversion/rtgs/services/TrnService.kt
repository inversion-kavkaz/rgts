package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.inversion.rtgs.dto.TrnDTO
import ru.inversion.rtgs.entity.RtgsTrn
import ru.inversion.rtgs.facade.TrnFacade
import ru.inversion.rtgs.payload.request.TrnRequest
import ru.inversion.rtgs.repository.TrnRepository
import java.time.LocalDate
import java.util.stream.Collectors

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

@Service
class TrnService @Autowired constructor(
        private val trnRepository: TrnRepository,
        private val trnFacade: TrnFacade
) {


    fun create(trn : RtgsTrn) : RtgsTrn {
        return trnRepository.save(trn)
    }

    fun update(trn : RtgsTrn) : RtgsTrn {
        return trnRepository.save(trn)
    }

    fun delete(trnId : Long) = trnRepository.deleteById(trnId)

    fun getAllUserTrnByDate(trnReq : TrnRequest) : MutableList<TrnDTO>? {
        val userId = trnReq.user_id
        if(userId == null) return mutableListOf()
        var ld: LocalDate? = LocalDate.parse(trnReq.date)
        if(ld == null) ld = LocalDate.now()
        return ld?.let {
            trnRepository.getAllByDate(it,userId)
                    .stream()
                    .map{t -> trnFacade.trnToTrnFacade(t)}
                    .collect(Collectors.toList())
        }
        }


    }
