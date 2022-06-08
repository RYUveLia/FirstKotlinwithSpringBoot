package com.hi.raul.controller.exception

import com.hi.raul.model.http.ErrorResponse
import com.hi.raul.model.http.Error
import com.hi.raul.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api/exception")
@Validated
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello(){
        val list = mutableListOf<String>()
        val temp = list[0]
    }

    @GetMapping("")
    fun get(
        @NotBlank
        @Size(min = 2, max = 6)
        @RequestParam name:String,

        @Min(10)
        @RequestParam age:Int
    ): String {
        println(name)
        println(age)
        return name + " " + age
    }

    @PostMapping("")
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach{ errorObject->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }

            errors.add(error)
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.message = "요청에 에러가 발생했습니다."
            this.httpMethod = request.method
            this.errors = errors
            this.timestamp = LocalDateTime.now()
            this.path = request.requestURI.toString()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationExceipton(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        // 1. 에러 분석
        val errors = mutableListOf<Error>()

        e.constraintViolations.forEach{
            val error = Error().apply {
                this.field = it.propertyPath.last().name
                this.message = it.message
                this.value = it.invalidValue
            }
            errors.add(error)
        }
        // 2. error response
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.message = "요청에 에러가 발생했습니다."
            this.httpMethod = request.method
            this.errors = errors
            this.timestamp = LocalDateTime.now()
            this.path = request.requestURI.toString()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    // 해당 컨트롤러 안에서만 일어남
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e : IndexOutOfBoundsException): ResponseEntity<String> {
        println("controller exception handler")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("index Error")
    }
}