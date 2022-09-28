package com.example.Employee.Controller

import com.example.Employee.Model.Employee
import com.example.Employee.Service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
@RestController
@RequestMapping("employee")
class EmployeeController(
    @Autowired
    val empSer: EmployeeService){

    @GetMapping("lists")
        fun getAllEmployee(): Flux<Employee> {
            return empSer.findAllEmployee()
        }

    @GetMapping("find/{empId}")
    fun findEmployee(@PathVariable("empId") empId: Int) : Mono<Employee> {
        return empSer.findById(empId)
    }

    @PostMapping("add")
    fun createEmployee(@RequestBody employee: Employee): Mono<Employee>{
        return empSer.addEmployee(employee)
    }

    @PutMapping("update/{empId}")
    fun updateEmployeeById(@PathVariable("empId") empId: Int, @RequestBody employee: Employee): Mono<Employee>{
        return empSer.updateEmployee(empId, employee)
    }

    @DeleteMapping("delete/{empId}")
    fun deleteEmployeeById(@PathVariable("empId") empId: Int): Mono<Void>{
        return empSer.deleteById(empId)
    }
    }