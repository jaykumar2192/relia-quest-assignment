package com.reliaquest.api.repository;

import com.reliaquest.api.data.EmployeeEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link EmployeeEntity}. Provides methods to perform CRUD operations and
 * custom queries on the "employees" table.
 *
 * <p>This interface extends {@link JpaRepository} to inherit basic database operations and
 * declares custom query methods to support searching by name, finding the highest salary, and
 * retrieving top earners.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

  /**
   * Finds a list of employees whose names contain the given substring (case-insensitive).
   *
   * @param name the name fragment to search for
   * @return a list of matching {@link EmployeeEntity} instances
   */
  @Query("SELECT e FROM EmployeeEntity e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<EmployeeEntity> findByNameContainingIgnoreCase(@Param("name") String name);

  /**
   * Retrieves the highest salary among all employees.
   *
   * @return the maximum salary, or {@code null} if no employees exist
   */
  @Query("SELECT MAX(e.salary) FROM EmployeeEntity e")
  Integer findMaxSalary();

  /**
   * Finds the top 10 employees with the highest salaries in descending order.
   *
   * @return a list of top 10 highest-earning {@link EmployeeEntity} instances
   */
  List<EmployeeEntity> findTop10ByOrderBySalaryDesc();
}
