package com.hi.raul.model.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.hi.raul.annotation.StringFormatDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*

/*
 * jsonNaming or JsonProperty를 통해 camel, snake case (camelCase, snake_case)를 해결가능
 */
data class UserRequest (

    @field:NotEmpty
    @field:Size(min = 2, max = 8)
    var name:String?=null,

    @field:PositiveOrZero
    var age:Int?=null,

    @field:Email
    var email:String?=null,

    @field:NotBlank // 공백 검증
    var address:String?=null,

    @field:Pattern(regexp =  "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber:String?=null,

    @field:StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다.")
    var createdAt:String?=null
)