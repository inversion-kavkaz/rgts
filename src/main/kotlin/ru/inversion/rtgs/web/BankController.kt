package ru.inversion.rtgs.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.inversion.rtgs.dto.BankDTO
import ru.inversion.rtgs.entity.RtgsBank
import ru.inversion.rtgs.facade.BankFacade
import ru.inversion.rtgs.services.BankService
import java.util.stream.Collectors

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */

@RestController
@CrossOrigin
@RequestMapping("/bank")
@PreAuthorize("permitAll()")
class BankController{

    @Autowired
    private val bankService: BankService? = null
    @Autowired
    private val bankFacade: BankFacade? = null

    @GetMapping("/allInfo")
    fun getAllBankMoreInformation() : ResponseEntity<List<RtgsBank>>{
        return ResponseEntity(bankService!!.getAllBanksFullInformation(), HttpStatus.OK)
    }

    @GetMapping("/all")
    fun getAllBankList() : ResponseEntity<List<BankDTO>>{
        val bankDTOList: List<BankDTO> = bankService!!.getAllBanksFullInformation()?.stream()
                ?.map { p -> bankFacade?.bankToBankDTO(p) }
                ?.collect(Collectors.toList()) as List<BankDTO>? ?: emptyList()
        return ResponseEntity(bankDTOList, HttpStatus.OK)
    }


}