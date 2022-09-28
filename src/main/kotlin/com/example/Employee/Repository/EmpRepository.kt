package com.example.Employee.Repository

import com.example.Employee.Model.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EmpRepository : ReactiveMongoRepository<Employee,Int> {
}