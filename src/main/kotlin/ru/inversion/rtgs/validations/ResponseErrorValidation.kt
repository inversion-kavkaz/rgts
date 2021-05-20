package ru.inversion.rtgs.validations

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.validation.BindingResult
import java.util.*

@Service
class ResponseErrorValidation {
    fun mapValidationService(result: BindingResult): ResponseEntity<Any>? {
        if (result.hasErrors()) {
            val errorMap: MutableMap<String?, String?> = HashMap()
            if (!CollectionUtils.isEmpty(result.allErrors)) {
                for (error in result.allErrors) {
                    errorMap[error.code] = error.defaultMessage
                }
            }
            for (error in result.fieldErrors) {
                errorMap[error.field] = error.defaultMessage
            }
            return ResponseEntity(errorMap, HttpStatus.BAD_REQUEST)
        }
        return null
    }
}
