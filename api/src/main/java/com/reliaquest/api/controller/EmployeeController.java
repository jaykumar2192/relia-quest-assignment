package com.reliaquest.api.controller;

import com.reliaquest.api.data.EmployeeRequest;
import com.reliaquest.api.service.IEmployeeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController implements IEmployeeController<EmployeeRequest, EmployeeRequest> {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
  IEmployeeService employeeService;

  @Autowired
  EmployeeController(IEmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Override
  public ResponseEntity<List<EmployeeRequest>> getAllEmployees() {
    return employeeService
        .getAllEmployee()
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error("EmployeeController#getAllEmployees Error getting all employee info", t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }

  @Override
  public ResponseEntity<List<EmployeeRequest>> getEmployeesByNameSearch(String searchString) {
    return employeeService
        .getEmployeesByNameSearch(searchString)
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error(
              "EmployeeController#getEmployeesByNameSearch Error searching for employee(s) with name:{}",
              searchString,
              t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }

  @Override
  public ResponseEntity<EmployeeRequest> getEmployeeById(String id) {
    return employeeService
        .getEmployeeById(id)
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error(
              "EmployeeController#getEmployeeById Error getting information for employee with Id:{}",
              id,
              t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }

  @Override
  public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
    return employeeService
        .getHighestSalaryOfEmployee()
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error(
              "EmployeeController#getHighestSalaryOfEmployees Error fetching highest salary", t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }

  @Override
  public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
    return employeeService
        .getTopTenHighestEarningEmployeeNames()
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error(
              "EmployeeController#getTopTenHighestEarningEmployeeNames Error fetching top 10 highest earning employee info",
              t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }

  @Override
  public ResponseEntity<EmployeeRequest> createEmployee(EmployeeRequest employeeRequestInput) {
    return employeeService
        .createEmployee(employeeRequestInput)
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error(
              "EmployeeController#createEmployee Error creating new employee:{}",
              employeeRequestInput,
              t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }

  @Override
  public ResponseEntity<String> deleteEmployeeById(String id) {
    return employeeService
        .deleteEmployeeById(id)
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          logger.error("EmployeeController#deleteEmployeeById Error deleting employee with Id:{}",
              id, t);
          return ResponseEntity.internalServerError().build();
        })
        .join();
  }
}
