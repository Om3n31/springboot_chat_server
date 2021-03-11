package com.example.demo

import com.example.demo.model.UserBean
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JsonContentAssert

@SpringBootTest
class DemoApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun testConnexion() {
        var jsonParser = JsonParser()
        val json = "{\"dick\":\"Hello\", \"okok\":\"noway\"}"
        val jsonObject = jsonParser.parse(json)

        val testUser = UserBean(5649469463416)
        Assertions.assertThrows(Exception::class.java, {
            MyControler().userConnexion(testUser)
        })
    }
}
