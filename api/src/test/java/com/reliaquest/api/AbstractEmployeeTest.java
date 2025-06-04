package com.reliaquest.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.data.EmployeeRequest;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class AbstractEmployeeTest {

  public static final String EMAIL_TEMPLATE = "%s@company.com";
  private static final Logger logger = LoggerFactory.getLogger(AbstractEmployeeTest.class);

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  Faker faker = new Faker(Locale.getDefault());

  protected MvcResult createEmployeeRequest() {
    try {
      return mockMvc.perform(post("/")
              .contentType(MediaType.APPLICATION_JSON)
              .content(getJsonEmployeeCreateRequest(createSampleEmployee())))
          .andExpect(status().isOk())
          .andReturn();
    } catch (Exception e) {
      logger.error("AbstractEmployeeTest#createEmployeeRequest Unable to perform create request",
          e);
      return null;
    }
  }

  protected EmployeeRequest createSampleEmployee() {
    return EmployeeRequest.builder()
        .id(UUID.randomUUID())
        .name(faker.name().fullName())
        .age(faker.number().numberBetween(16, 60))
        .salary(faker.number().numberBetween(10000, 90000))
        .title(faker.job().title())
        .email(EMAIL_TEMPLATE.formatted(faker.twitter().userName().toLowerCase()))
        .build();
  }

  protected String getJsonEmployeeCreateRequest(EmployeeRequest employeeRequest) {
    try {
      return objectMapper.writeValueAsString(createSampleEmployee());
    } catch (JsonProcessingException e) {
      logger.error(
          "AbstractEmployeeTest#convertEmployeeObjectToJson Error converting Employee Object:{} to Json",
          employeeRequest,
          e);
      return null;
    }
  }

  protected EmployeeRequest convertJsonToEmployeeRequest(MvcResult r) {
    try {
      String jsonResponse = r.getResponse().getContentAsString();
      return objectMapper.readValue(jsonResponse, EmployeeRequest.class);
    } catch (Exception e) {
      logger.error("AbstractEmployeeTest#convertJsonToObject Error converting Json to Object", e);
      return null;
    }
  }

  protected List<EmployeeRequest> convertStringToEmployeeRequestList(String responseString) {
    try {
      return objectMapper.readValue(responseString, new TypeReference<List<EmployeeRequest>>() {
      });
    } catch (Exception e) {
      logger.error("AbstractEmployeeTest#convertJsonToObject Error converting Json to Object", e);
      return null;
    }
  }


}
