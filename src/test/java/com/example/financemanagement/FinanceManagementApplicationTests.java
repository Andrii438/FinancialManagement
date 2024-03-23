package com.example.financemanagement;

import com.example.financemanagement.entity.User;
import com.example.financemanagement.entity.builder.UserBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/scripts/test_data.sql")
class FinanceManagementApplicationTests {

    public static final LocalDate LOCAL_DATE = LocalDate.of(1997, 12, 22);
    public static final String FIRST_NAME = "Andrii";
    public static final String SURNAME = "Mykhalchuk";
    public static final String USERNAME = "amykh";

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void getUsersIntegrationTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/user/")
                .then()
                .statusCode(200)
                .body(".", hasSize(3))
                .body("[0].username", equalTo("johndoe123"))
                .body("[1].username", equalTo("alicesmith456"))
                .body("[2].username", equalTo("bobjohnson789"));
    }

    @Test
    void postUserIntegrationTest() {
        User user = new UserBuilder()
                .birthdate(LOCAL_DATE)
                .firstName(FIRST_NAME)
                .surname(SURNAME)
                .username(USERNAME)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/v1/user/create")
                .then()
                .statusCode(201)
                .header("Location", "http://localhost:8081/v1/user/7");
    }
}
