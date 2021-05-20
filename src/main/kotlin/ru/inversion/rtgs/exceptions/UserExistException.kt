package ru.inversion.rtgs.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserExistException(message: String?) : RuntimeException(message)
