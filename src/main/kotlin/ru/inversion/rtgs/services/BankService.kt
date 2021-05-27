package ru.inversion.rtgs.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.inversion.rtgs.entity.RtgsBank
import ru.inversion.rtgs.repository.BankRepository

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

@Service
class BankService @Autowired constructor(private val bankRepository: BankRepository){

    fun getAllBanksFullInformation() : List<RtgsBank>? {
        return bankRepository.findAll()
    }

    fun getById(bankId: Long): RtgsBank? = bankRepository.findByIdOrNull(bankId)
}
