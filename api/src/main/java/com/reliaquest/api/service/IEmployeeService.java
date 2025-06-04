package com.reliaquest.api.service;

import com.reliaquest.api.data.EmployeeRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service interface for managing employee operations.
 * <p>
 * This interface defines asynchronous operations related to employee management, such as creating,
 * retrieving, searching, and deleting employees. Implementations of this interface are responsible
 * for the business logic and data interaction.
 */
public interface IEmployeeService {

  /**
   * Retrieves a list of all employees.
   *
   * @return a {@link CompletableFuture} containing a list of {@link EmployeeRequest} objects.
   */
  CompletableFuture<List<EmployeeRequest>> getAllEmployee();

  /**
   * Searches for employees by name. The search is case-insensitive and supports partial matches.
   *
   * @param employeeName the name or partial name of the employee to search for.
   * @return a {@link CompletableFuture} containing a list of matching {@link EmployeeRequest}
   * objects.
   */
  CompletableFuture<List<EmployeeRequest>> getEmployeesByNameSearch(String employeeName);

  /**
   * Retrieves a single employee by their unique identifier.
   *
   * @param employeeId the UUID of the employee (as a string).
   * @return a {@link CompletableFuture} containing the {@link EmployeeRequest} of the found
   * employee.
   */
  CompletableFuture<EmployeeRequest> getEmployeeById(String employeeId);

  /**
   * Retrieves the highest salary among all employees.
   *
   * @return a {@link CompletableFuture} containing the highest salary as an {@link Integer}.
   */
  CompletableFuture<Integer> getHighestSalaryOfEmployee();

  /**
   * Retrieves the names of the top ten highest-earning employees.
   *
   * @return a {@link CompletableFuture} containing a list of employee names.
   */
  CompletableFuture<List<String>> getTopTenHighestEarningEmployeeNames();

  /**
   * Creates a new employee record in the system.
   *
   * @param employeeRequest the {@link EmployeeRequest} object containing the employee details.
   * @return a {@link CompletableFuture} containing the created {@link EmployeeRequest} object.
   */
  CompletableFuture<EmployeeRequest> createEmployee(EmployeeRequest employeeRequest);

  /**
   * Deletes an employee from the system by their unique identifier.
   *
   * @param employeeId the UUID of the employee (as a string).
   * @return a {@link CompletableFuture} containing a confirmation message.
   */
  CompletableFuture<String> deleteEmployeeById(String employeeId);
}
