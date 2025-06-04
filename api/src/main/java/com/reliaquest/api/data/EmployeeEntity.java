package com.reliaquest.api.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity class representing an employee in the system.
 * <p>
 * This class is mapped to the "employees" table in the database using JPA annotations. It includes
 * basic employee attributes like ID, name, salary, age, title, and email. Lombok annotations are
 * used to reduce boilerplate code by generating getters, setters, constructors, and the toString
 * method automatically.
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeEntity {

  /**
   * The unique identifier for the employee.
   */
  @Id
  private UUID id;

  /**
   * The full name of the employee.
   */
  private String name;


  /**
   * The salary of the employee.
   */
  private Integer salary;

  /**
   * The age of the employee.
   */
  private Integer age;

  /**
   * The job title or designation of the employee.
   */
  private String title;

  /**
   * The email address of the employee.
   */
  private String email;
}
