package ru.inversion.rtgs.facade

import org.springframework.stereotype.Component
import ru.inversion.rtgs.dto.BankDTO
import ru.inversion.rtgs.entity.RtgsBank

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

@Component
class BankFacade {
        fun bankToBankDTO(bank: RtgsBank) : BankDTO = BankDTO(bank.id,bank.bik,bank.bankAdress,bank.bankName)
    }
