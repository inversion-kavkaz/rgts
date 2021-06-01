package ru.inversion.rtgs.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.inversion.rtgs.payload.request.BalanceRequest
import ru.inversion.rtgs.services.BalanceService
import java.time.LocalDate

/**
 * @author Dmitry Hvastunov
 * @created 01.06.2021
 * @project rtgs
 */

@CrossOrigin
@RestController
@RequestMapping("/balance")
@PreAuthorize("permitAll()")
class BalanceController(private val balanceService: BalanceService) {

    @PostMapping("get")
    fun getBalance(@RequestBody balReq: BalanceRequest): ResponseEntity<Any>? {
        if (balReq.data == null)
            balReq.data = LocalDate.now().plusDays(1).toString()
        if (balReq.acc == null)
            return ResponseEntity("ERROR not corr accaunt", HttpStatus.OK)

        return ResponseEntity(balanceService.getBalance(balReq.data!!, balReq.curr!!, balReq.acc!!), HttpStatus.OK)

    }

}