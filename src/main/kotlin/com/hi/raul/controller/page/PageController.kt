package com.hi.raul.controller.page

import com.hi.raul.model.http.UserRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller // return값을 그대로 내보내는 것이 아니라 statics 폴더에서 찾음
class PageController {
    @GetMapping("/main")
    fun main(): String {
        println("init main")
        return "main.html"
    }

    @ResponseBody // 붙으면 plain text를 보내줌
    @GetMapping("/test")
    fun response(): UserRequest {
        return UserRequest().apply{
            this.name = "steve"
        }
    }
}