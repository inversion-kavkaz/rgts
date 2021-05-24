package ru.inversion.rtgs.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.inversion.rtgs.entity.RtgsTrn
import ru.inversion.rtgs.payload.request.TrnRequest
import ru.inversion.rtgs.repository.TrnRepository
import ru.inversion.rtgs.services.TrnService
import ru.inversion.rtgs.validations.ResponseErrorValidation
import java.security.Principal
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

/**
 * @author Dmitry Hvastunov
 * @created 21.05.2021
 * @project rtgs
 */
@CrossOrigin
@RestController
@RequestMapping("/api/trn")
@PreAuthorize("permitAll()")
class TrnController @Autowired constructor(private val trnSrevice: TrnService,
                                           private val trnRepository: TrnRepository,
                                           private val responseErrorValidation: ResponseErrorValidation
) {

    @PostMapping("/create")
    fun createTrn(@RequestBody trn: @Valid RtgsTrn, bindingResult: BindingResult, principal: Principal): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        return ResponseEntity(trnSrevice.create(trn), HttpStatus.OK)
    }

    @PostMapping("/update")
    fun updateTrn(@RequestBody trn: @Valid RtgsTrn, bindingResult: BindingResult, principal: Principal): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        return ResponseEntity(trnSrevice.update(trn), HttpStatus.OK)
    }

    @DeleteMapping("delete/{id}")
    fun deleteTrn(@PathVariable id: Long): ResponseEntity<Any> {
        trnSrevice.delete(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PostMapping("/getAll")
    fun getAllByDate(@RequestBody date: TrnRequest, bindingResult: BindingResult, principal: Principal): ResponseEntity<List<RtgsTrn>>? {
//        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
//        if (!ObjectUtils.isEmpty(errors)) return errors
        var ld: LocalDate? = LocalDate.parse(date.date)
        if(ld == null) ld = LocalDate.now()
        val tl: MutableList<RtgsTrn>? = ld?.let { trnRepository.getAllByDate(it) }
        return ResponseEntity(tl,HttpStatus.OK)
    }

}