package com.reliaquest.api;

import static com.reliaquest.api.utils.EmployeeConstants.DUPLICATE_EMPLOYEE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.reliaquest.api.data.EmployeeRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class EmployeeControllerTest extends AbstractEmployeeTest {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerTest.class);

  @Test
  void testCreateAndGetEmployee() throws Exception {
    EmployeeRequest employee = createSampleEmployee();
    MvcResult result = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)))
        .andExpect(status().isOk())
        .andReturn();
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
    EmployeeRequest savedEmp = convertJsonToEmployeeRequest(result);

    result = mockMvc.perform(get("/" + savedEmp.getId()))
        .andExpect(status().isOk())
        .andReturn();
    savedEmp = convertJsonToEmployeeRequest(result);
    Assertions.assertThat(savedEmp).isEqualTo(employee);
  }

  @Test
  void testCreateEmployeeWithDuplicateId() throws Exception {
    EmployeeRequest employee = createSampleEmployee();
    MvcResult result = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)))
        .andExpect(status().isOk())
        .andReturn();
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);

    result = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)))
        .andExpect(status().is5xxServerError())
        .andReturn();
    Assertions.assertThat(result.getResponse().getHeader("X-Error-Message"))
        .contains(DUPLICATE_EMPLOYEE);
  }

  @Test
  void testDeleteEmployeeById() throws Exception {
    EmployeeRequest employee = createSampleEmployee();
    MvcResult result = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)))
        .andExpect(status().isOk())
        .andReturn();
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
    result = mockMvc.perform(delete("/" + employee.getId()))
        .andExpect(status().isOk())
        .andReturn();
    String jsonResponse = result.getResponse().getContentAsString();
    Assertions.assertThat(jsonResponse).isEqualTo("Deleted");
  }

  @Test
  void testDeleteEmployeeWhenNotPresent() throws Exception {
    MvcResult result = mockMvc.perform(delete("/" + UUID.randomUUID()))
        .andExpect(status().is5xxServerError())
        .andReturn();
    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(500);
  }

  @Test
  void testConcurrentlyCreateEmployeesAndGetHighestSalary() throws Exception {

    CompletableFuture<MvcResult>[] createEmployeesRequest = IntStream.range(0, 20)
        .mapToObj(i -> CompletableFuture.supplyAsync(() -> createEmployeeRequest()))
        .toArray(CompletableFuture[]::new);

    List<MvcResult> allResult = Arrays.stream(createEmployeesRequest)
        .map(CompletableFuture::join)
        .toList();

    Assertions.assertThat(allResult).hasSize(20);

    MvcResult result = mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andReturn();

    List<EmployeeRequest> listEmployeeRequest = convertStringToEmployeeRequestList(
        result.getResponse().getContentAsString());

    Optional<EmployeeRequest> maxEmployeeSalary = listEmployeeRequest.stream()
        .max(Comparator.comparingInt(e -> {
          Assertions.assertThat(e).isNotNull();
          return e.getSalary();
        }));

    Assertions.assertThat(maxEmployeeSalary).isPresent();
    result = mockMvc.perform(get("/highestSalary"))
        .andExpect(status().isOk())
        .andReturn();
    Integer maxSalary = Integer.parseInt(result.getResponse().getContentAsString());
    Assertions.assertThat(maxSalary).isEqualTo(maxEmployeeSalary.get().getSalary());
  }

  @Test
  void testGetTopTenHighestSalaryEmployees() throws Exception {

    CompletableFuture<MvcResult>[] createEmployeesRequest = IntStream.range(0, 20)
        .mapToObj(i -> CompletableFuture.supplyAsync(() -> createEmployeeRequest()))
        .toArray(CompletableFuture[]::new);

    List<MvcResult> allResult = Arrays.stream(createEmployeesRequest)
        .map(CompletableFuture::join)
        .toList();

    Assertions.assertThat(allResult).hasSize(20);
    MvcResult result = mockMvc.perform(get("/topTenHighestEarningEmployeeNames"))
        .andExpect(status().isOk())
        .andReturn();
    String allEmployeesName = result.getResponse().getContentAsString();
    Assertions.assertThat(allEmployeesName).isNotNull();
    String[] listEmployees = allEmployeesName.split(",");
    Assertions.assertThat(listEmployees).hasSize(10);
  }
}
