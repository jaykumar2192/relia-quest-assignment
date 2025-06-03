package com.reliaquest.api.service;

import static com.reliaquest.api.utils.EmployeeConstants.EMPLOYEE_NOT_FOUND_WITH_ID;
import static com.reliaquest.api.utils.EmployeeConstants.EMPLOYEE_NOT_FOUND_WITH_NAME;

import com.reliaquest.api.data.EmployeeEntity;
import com.reliaquest.api.data.EmployeeRequest;
import com.reliaquest.api.exception.EmployeeNotFoundException;
import com.reliaquest.api.repository.EmployeeRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository repository;
    private final EmployeeHelperService employeeHelperService;

    @Autowired
    EmployeeService(EmployeeRepository repository, EmployeeHelperService employeeHelperService) {
        this.repository = repository;
        this.employeeHelperService = employeeHelperService;
    }

    @Override
    public CompletableFuture<List<EmployeeRequest>> getAllEmployee() {
        if (logger.isDebugEnabled()) {
            logger.debug("EmployeeService#getAllEmployee Fetching list of all employees");
        }
        return CompletableFuture.supplyAsync(() ->
                repository.findAll().stream().map(employeeHelperService::toDto).collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<EmployeeRequest>> getEmployeesByNameSearch(String employeeName) {
        if (logger.isDebugEnabled()) {
            logger.debug("EmployeeService#getEmployeesByNameSearch Searching for employee(s) by name:{}", employeeName);
        }
        return CompletableFuture.supplyAsync(() -> {
            List<EmployeeRequest> result = repository.findByNameContainingIgnoreCase(employeeName).stream()
                    .map(employeeHelperService::toDto)
                    .collect(Collectors.toList());
            if (result.isEmpty()) {
                throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_WITH_NAME + employeeName);
            }
            return result;
        });
    }

    @Override
    public CompletableFuture<EmployeeRequest> getEmployeeById(String employeeId) {
        if (logger.isDebugEnabled()) {
            logger.debug("EmployeeService#getEmployeeById Searching for employee(s) with Id:{}", employeeId);
        }
        return CompletableFuture.supplyAsync(() -> {
            UUID id = UUID.fromString(employeeId);
            return repository
                    .findById(id)
                    .map(employeeHelperService::toDto)
                    .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
        });
    }

    @Override
    public CompletableFuture<Integer> getHighestSalaryOfEmployee() {
        if (logger.isDebugEnabled()) {
            logger.debug("EmployeeService#getHighestSalaryOfEmployee Fetching employee with highest salary");
        }
        return CompletableFuture.supplyAsync(() -> {
            Integer maxSalary = repository.findMaxSalary();
            return maxSalary != null ? maxSalary : 0;
        });
    }

    @Override
    public CompletableFuture<List<String>> getTopTenHighestEarningEmployeeNames() {
        if (logger.isDebugEnabled()) {
            logger.debug(
                    "EmployeeService#getTopTenHighestEarningEmployeeNames Fetching top 10 employee with highest salary");
        }
        return CompletableFuture.supplyAsync(() -> repository.findTop10ByOrderBySalaryDesc().stream()
                .map(EmployeeEntity::getName)
                .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<EmployeeRequest> createEmployee(EmployeeRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug("EmployeeService#createEmployee Creating name with following details:{}", request);
        }
        return CompletableFuture.supplyAsync(() -> {
            UUID id = request.getId() != null ? request.getId() : UUID.randomUUID();
            EmployeeEntity entity =
                    employeeHelperService.toEntity(request.toBuilder().id(id).build());
            EmployeeEntity saved = repository.save(entity);
            return employeeHelperService.toDto(saved);
        });
    }

    @Override
    public CompletableFuture<String> deleteEmployeeById(String employeeId) {
        if (logger.isDebugEnabled()) {
            logger.debug("EmployeeService#deleteEmployeeById Deleting employee with EmployeeId:{}", employeeId);
        }
        return CompletableFuture.supplyAsync(() -> {
            UUID id = UUID.fromString(employeeId);
            EmployeeEntity entity = repository
                    .findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
            repository.delete(entity);
            return "Deleted";
        });
    }
}
