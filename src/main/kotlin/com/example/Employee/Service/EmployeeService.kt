package com.example.Employee.Service

import com.example.Employee.Model.Employee
import com.example.Employee.Repository.EmpRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class EmployeeService (
    @Autowired
    val empRepo: EmpRepository
){
    fun findAllEmployee(): Flux<Employee> {

        return empRepo.findAll()

    }

    fun findById(empId: Int): Mono<Employee> {
         return empRepo.findById(empId)
    }

    fun addEmployee(employee: Employee): Mono<Employee> {
        return empRepo.save(employee)

    }

    fun updateEmployee(empId: Int, employee: Employee): Mono<Employee> {
        return empRepo.findById(empId)
            .flatMap {
                it.empId = employee.empId
                it.empName = employee.empName
                it.empPosition = employee.empPosition
                empRepo.save(it)
            }

    }

    fun deleteById(empId: Int): Mono<Void> {
        return empRepo.deleteById(empId)

    }

}
