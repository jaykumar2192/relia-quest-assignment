package com.reliaquest.api.controller;

import static com.reliaquest.api.utils.EmployeeConstants.FAILED_TO_CREATE_NEW_EMPLOYEE;
import static com.reliaquest.api.utils.EmployeeConstants.FAILED_TO_DELETE_EMPLOYEE;
import static com.reliaquest.api.utils.EmployeeConstants.FAILED_TO_FETCH_HIGHEST_SALARY;
import static com.reliaquest.api.utils.EmployeeConstants.FAILED_TO_FETCH_TOP_TEN_HIGHEST_SALARY;

import com.reliaquest.api.data.EmployeeRequest;
import com.reliaquest.api.service.IEmployeeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController implements IEmployeeController<EmployeeRequest, EmployeeRequest> {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
  private static final String X_ERROR_MESSAGE = "X-Error-Message";
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
          String errorMessage = FAILED_TO_FETCH_HIGHEST_SALARY + t.getMessage();
          HttpHeaders headers = new HttpHeaders();
          headers.add(X_ERROR_MESSAGE, errorMessage);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .headers(headers)
              .body(-1);
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
          String errorMessage = FAILED_TO_FETCH_TOP_TEN_HIGHEST_SALARY + t.getMessage();
          HttpHeaders headers = new HttpHeaders();
          headers.add(X_ERROR_MESSAGE, errorMessage);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .headers(headers)
              .build();
        })
        .join();
  }

  @Override
  public ResponseEntity<EmployeeRequest> createEmployee(EmployeeRequest employeeRequestInput) {
    return employeeService
        .createEmployee(employeeRequestInput)
        .thenApply(ResponseEntity::ok)
        .exceptionally(t -> {
          String errorMessage = FAILED_TO_CREATE_NEW_EMPLOYEE + t.getMessage();
          HttpHeaders headers = new HttpHeaders();
          headers.add(X_ERROR_MESSAGE, errorMessage);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .headers(headers)
              .build();
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
          String errorMessage = FAILED_TO_DELETE_EMPLOYEE + t.getMessage();
          HttpHeaders headers = new HttpHeaders();
          headers.add(X_ERROR_MESSAGE, errorMessage);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .headers(headers)
              .build();
        })
        .join();
  }
}
