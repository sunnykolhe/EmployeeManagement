package com.example.Employee.Service

import com.example.Employee.Model.Employee
import com.example.Employee.Repository.EmpRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class EmployeeServiceTest {
    val emp1 = Employee(1, "Sunny",  "Supervisor")
    val emp2 = Employee(2, "Saurabh",  "Soft Developer")

    private val empRepository = mockk<EmpRepository>()  {

        every {
            findAll()
        } returns Flux.just(emp1, emp2)

        every {
            findById(1)
        } returns Mono.just(emp1)


    }

    private val empService = EmployeeService(empRepository)

    @Test
    fun `should return employees when findAllUsers  method is called`() {

        val firstEmployee = empService.findAllEmployee().blockFirst()
        val secondEmployee = empService.findAllEmployee().blockLast()

        if (firstEmployee != null) {
            firstEmployee shouldBe emp1
        }
        if (secondEmployee != null) {
            secondEmployee shouldBe emp2
        }
    }

    @Test
    fun `Test adding Employee`() {
        val emp1 = Employee(1, "Sunny",  "Supervisor")

        every{
            empRepository.save(emp1)
        } returns Mono.just(emp1)

        val addedUser = empService.addEmployee(emp1).block()

        addedUser shouldBe emp1
    }

    @Test
    fun `Test Find Employee By Id`() {

        val result=empService.findById(1).block()

        result shouldBe emp1

    }

    @Test
    fun `delete Employee By Id`() {

        every{
            empRepository.deleteById(1)
        }returns Mono.empty()
    }

    @Test
    fun `Test update Employee`() {

        // val user1 = User(999,"Rahul K",9999999999,"Aaaaa@aaa")
        every{
            empRepository.save(emp1)
        }returns Mono.just(emp1)
        val updatedUser = empService.updateEmployee(1,emp1).block()

        updatedUser shouldBe emp1
    }


}




