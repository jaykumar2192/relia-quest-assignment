package com.reliaquest.api.service;

import com.reliaquest.api.data.EmployeeRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IEmployeeService {

    CompletableFuture<List<EmployeeRequest>> getAllEmployee();

    CompletableFuture<List<EmployeeRequest>> getEmployeesByNameSearch(String employeeName);

    CompletableFuture<EmployeeRequest> getEmployeeById(String employeeId);

    CompletableFuture<Integer> getHighestSalaryOfEmployee();

    CompletableFuture<List<String>> getTopTenHighestEarningEmployeeNames();

    CompletableFuture<EmployeeRequest> createEmployee(EmployeeRequest employeeRequest);

    CompletableFuture<String> deleteEmployeeById(String employeeId);
}
