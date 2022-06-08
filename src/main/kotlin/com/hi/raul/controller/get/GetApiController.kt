package com.hi.raul.controller.get

import com.hi.raul.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class GetApiController {

    @GetMapping(path = ["/hello", "/abcd"]) // GET http://localhost:8080/api/hello, GET http://localhost:8080/api/abcd
    fun hello(): String{
        return "Hello Kotlin"
    }

    @RequestMapping(method = [RequestMethod.GET], path = ["request-mapping"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}")    // GET http://localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable(@PathVariable name: String, @PathVariable age: Int): String{
        println("${name}, ${age}")
        return name+" " +age
    }

    @GetMapping("/get-mapping/path-variable2/{name}/{age}")    // GET http://localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable2(@PathVariable(value = "name") _name: String, @PathVariable(name = "age") age: Int): String{
        val name = "kotlin"

        println("${_name}, ${age}")
        return _name+" "+age
    }

    // 어노테이션 + 겟매핑 = 주소를 노출시키겠다는 뜻
    @GetMapping("/get-mapping/query-param") // ?name=steve&age=20
    fun queryParam(
        @RequestParam name: String,
        @RequestParam(value = "age") age:Int
    ): String{
        println("${name}, ${age}")
        return name+" "+age
    }

    // 쿼리 파라미터를 객체로 매핑하는 방법
    // name, age, address, email
    // phoneNumber -> phone-number (자바/코틀린과 uri의 차이) -> value = "phone-number"식으로 해야함
    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Any? {
        println(map)
        val phoneNumber = map.get("phone-number")
        return phoneNumber
    }
}