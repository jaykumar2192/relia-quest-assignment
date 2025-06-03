package com.reliaquest.api.repository;

import com.reliaquest.api.data.EmployeeEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    @Query("SELECT e FROM EmployeeEntity e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<EmployeeEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT MAX(e.salary) FROM EmployeeEntity e")
    Integer findMaxSalary();

    List<EmployeeEntity> findTop10ByOrderBySalaryDesc();
}
