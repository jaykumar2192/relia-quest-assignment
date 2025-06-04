package com.reliaquest.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) representing the structure of an employee request payload.
 * <p>
 * This class is used to receive and send employee data in API requests and responses. It is
 * annotated with Jackson annotations for JSON serialization/deserialization and Lombok annotations
 * to reduce boilerplate code like getters, setters, constructors, etc.
 * <p>
 * Unknown JSON properties will be ignored during deserialization to ensure flexibility.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class EmployeeRequest {

  /**
   * The unique identifier for the employee.
   */
  @JsonProperty("id")
  private UUID id;

  /**
   * The name of the employee. Mapped from the JSON property "employee_name".
   */
  @JsonProperty("employee_name")
  private String name;

  /**
   * The salary of the employee. Mapped from the JSON property "employee_salary".
   */
  @JsonProperty("employee_salary")
  private Integer salary;

  /**
   * The age of the employee. Mapped from the JSON property "employee_age".
   */
  @JsonProperty("employee_age")
  private Integer age;

  /**
   * The job title of the employee. Mapped from the JSON property "employee_title".
   */
  @JsonProperty("employee_title")
  private String title;

  /**
   * The email address of the employee. Mapped from the JSON property "employee_email".
   */
  @JsonProperty("employee_email")
  private String email;
}
