package com.hi.raul.advice

import com.hi.raul.controller.exception.ExceptionApiController
import com.hi.raul.controller.put.PutApiController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// @ControllerAdvice = html파일을 직접 소환할 때 쓸 수 있는 것
//@RestControllerAdvice(basePackageClasses = [PutApiController::class])
class GlobalControllerAdvice {

    @ExceptionHandler(value = [RuntimeException::class])
    fun exception(e : RuntimeException): String {
        return "Server Runtime Error"
    }

    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e : IndexOutOfBoundsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("index Error")
    }
}