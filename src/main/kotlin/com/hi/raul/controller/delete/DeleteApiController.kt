package com.hi.raul.controller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api")
@Validated // bean이 아니기 때문에 어노테이션 해줘야함
class DeleteApiController {
    @DeleteMapping(path = ["/delete-mapping"])
    fun deleteMapping(
        @RequestParam(value = "name") _name: String,

        @NotNull(message = "age가 누락되었습니다.")
        @Min(value = 20, message = "must be greater than 20")
        @RequestParam(value = "age") _age : Int
    ): String {
        println(_name)
        println(_age)

        return _name + " " + _age
    }

    @DeleteMapping(path = ["/delete-mapping/name/{name}/age/{age}"])
    fun deleteMappingPath(@PathVariable(value="name")
                          @Size(min = 2, max = 5)
                          @NotNull
                          _name:String,

                          @NotNull(message = "age가 누락되었습니다.")
                          @Min(20)
                          @PathVariable(value="age") _age:Int): String {
        return _name + " " + _age
    }
}