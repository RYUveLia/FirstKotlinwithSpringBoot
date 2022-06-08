package com.hi.raul.controller.put

import com.hi.raul.model.http.Result
import com.hi.raul.model.http.UserRequest
import com.hi.raul.model.http.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api")
class PutApiController {

    @PutMapping("/put-mapping")
    fun putMapping(): String {
        return "put-mapping"
    }

    @RequestMapping(method = [RequestMethod.PUT], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping - putr method"
    }

    //valid : 해당 bean에 대해서만 검증이 필요할 때
    @PutMapping(path = ["put-mapping/object"])
    fun putMappingObject(@Valid @RequestBody userRequest: UserRequest, bindingResult: BindingResult): Any {

        if(bindingResult.hasErrors()){
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append(field.field+" : "+message+"\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }

        return UserResponse().apply{
            this.result = Result().apply {
                this.resultCode = "OK"
                this.resultMessage = "성공"
            }
        }.apply {
            this.description = "~~~~~~~"
        }.apply {
            val userList = mutableListOf<UserRequest>()

            userList.add(userRequest)
            userList.add(UserRequest().apply {
                this.name = "a"
                this.age = 10
                this.email = "a@gmail.com"
                this.address = "seoul"
                this.phoneNumber = "010-0000-2312"
            })

            userList.add(UserRequest().apply {
                this.name = "b"
                this.age = 10
                this.email = "a@gmail.com"
                this.address = "seoul"
                this.phoneNumber = "010-0000-2312"
            })

            this.userRequest = userList
        }
    }
}