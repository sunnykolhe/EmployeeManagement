package com.example.Employee.Controller

import com.example.Employee.Model.Employee
import com.example.Employee.Service.EmployeeService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(EmployeeController::class)
@AutoConfigureWebTestClient
class EmployeeControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var empSer: EmployeeService

    @Test
    fun `should return list of employee`() {
        val emp1 = Employee(1,"Sunny","Supervisor")
        val emp2 = Employee(2,"Saurabh","Soft Developer")

        val expectedResult = listOf(
            mapOf(
                "empId" to 1,
                "empName" to "Sunny",
                "empPosition" to "Supervisor"
            ),
            mapOf(
                "empId" to 2,
                "empName" to "Saurabh",
                "empPosition" to "Soft Developer"
            ),
        )
        every {
            empSer.findAllEmployee()
        } returns Flux.just(emp1, emp2)

        val response = client.get()
            .uri("/employee/lists")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult[0]
        verify (exactly = 1) {
            empSer.findAllEmployee()
        }
    }

    @Test
    fun `should create employee when create api is being called`() {

        val exepectedResponse = mapOf(
            "empId" to 1,
            "empName" to "Sunny",
            "empPosition" to "Supervisor",)

        val emp = Employee(1, "Sunny", "Supervisor")

        every {
            empSer.addEmployee(emp)
        } returns Mono.just(emp)

        val response = client.post()
            .uri("/employee/add")
            .bodyValue(emp)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            empSer.addEmployee(emp)
        }
    }

    @Test
    fun `should be able to update the employee`() {

        val expectedResult =mapOf(
            "empId" to 1,
            "empName" to "Sunny K",
            "empPosition" to "Supervisor",)

        val emp = Employee(1,"Sunny K" , "Supervisor")

        every {
            empSer.updateEmployee(1,emp)
        } returns Mono.just(emp)

        val response = client.put()
            .uri("/employee/update/1")
            .bodyValue(emp)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            empSer.updateEmployee(1,emp)
        }
    }

    @Test
    fun `should be able to delete the employee`() {

        val expectedResult = listOf(
            mapOf( "empId" to 1,
                "empName" to "Sunny",
                "empPosition" to "Supervisor") )

        val emp = Employee(1,"Sunny" , "Supervisor")

        every {
            empSer.deleteById(1)
        } returns  Mono.empty()

        val response = client.delete()
            .uri("/employee/delete/1")
            .exchange()
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            empSer.deleteById(1)
        }
    }

    @Test
    fun `should return a single employee`() {

        val exepectedResponse =
            mapOf( "empId" to 1,
                "empName" to "Sunny",
                "empPosition" to "Supervisor")

        val emp=Employee(1,"Sunny","Supervisor")

        every {
            empSer.findById(1)
        } returns Mono.just(emp)



        val response = client.get()
            .uri("/employee/find/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            empSer.findById(1)
        }

    }
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun empSer() = mockk<EmployeeService>()
    }


}




