package ru.inversion.rtgs.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.ObjectUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.inversion.rtgs.entity.RtgsTrn
import ru.inversion.rtgs.facade.TrnFacade
import ru.inversion.rtgs.payload.response.TrnAffirmResponse
import ru.inversion.rtgs.payload.request.DeleteRequest
import ru.inversion.rtgs.payload.request.TrnAffirmRequest
import ru.inversion.rtgs.payload.request.TrnFilterRequest
import ru.inversion.rtgs.payload.request.TrnRequest
import ru.inversion.rtgs.repository.TrnRepository
import ru.inversion.rtgs.services.TrnService
import ru.inversion.rtgs.validations.ResponseErrorValidation
import java.security.Principal
import javax.validation.Valid

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
                                           private val responseErrorValidation: ResponseErrorValidation,
                                           private val trnFacade: TrnFacade
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

    @PostMapping("delete")
    fun deleteTrn(@RequestBody deleteRequest: DeleteRequest,bindingResult: BindingResult): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        val trnDelErrorList: MutableList<String> = mutableListOf()
        deleteRequest.idList?.stream()?.forEach {
            val status = trnSrevice.delete(it)
            if (status == null || status.startsWith("ERROR"))
                trnDelErrorList.add(it.toString() + status)
        }
            return ResponseEntity<Any>(trnDelErrorList,HttpStatus.OK)
    }

    @PostMapping("/getAll")
    fun getAllByDate(@RequestBody trnReq: TrnRequest, bindingResult: BindingResult, principal: Principal): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        return ResponseEntity(trnSrevice.getAllUserTrnByDate(trnReq),HttpStatus.OK)
    }

    @PostMapping("/getFilteringTrn")
    fun getAllByFilter(@RequestBody trnReq: TrnFilterRequest, bindingResult: BindingResult, principal: Principal): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        return ResponseEntity(trnSrevice.getAllUserTrnByFilter(trnReq),HttpStatus.OK)
    }

    @PostMapping("/affirm")
    fun affirmTrn(@RequestBody affirmList: TrnAffirmRequest, bindingResult: BindingResult, principal: Principal): ResponseEntity<Any>? {
        val errors = responseErrorValidation!!.mapValidationService(bindingResult!!)
        if (!ObjectUtils.isEmpty(errors)) return errors
        val trnAffirmErrorList: MutableList<TrnAffirmResponse> = mutableListOf()
        affirmList.idList?.stream()?.forEach {
                trnAffirmErrorList.add(trnSrevice.affirmTransaction(it))
        }
        return ResponseEntity<Any>(trnAffirmErrorList,HttpStatus.OK)

    }





}
