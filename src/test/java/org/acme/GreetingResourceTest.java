package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.acme.entities.Customer;

@QuarkusTest
class GreetingResourceTest {

  @Test
  void testHelloEndpoint() {
    given()
        .when().get("/hello")
        .then()
        .statusCode(200)
        .body(is("Hello from Quarkus REST"));
  }

  @Test
  void testGetCustomers() {
    given()
        .when().get("/customers")
        .then()
        .statusCode(200)
        .body(is("[]"));
  }

  @Test
  void testCreateCustomer() {
    var customer = Customer.find("name", "Nome").firstResult();
    assertNull(customer);

    given()
        .contentType(ContentType.JSON)
        .body("{\"name\":\"Nome\", \"password\":\"Senha\"}")
        .when().post("/customers")
        .then()
        .statusCode(200)
        .body("id", is(1))
        .body("name", is("Nome"))
        .body("password", is("Senha"));

    customer = Customer.find("name", "Nome").firstResult();
    assertNotNull(customer);
  }

  @Test
  void customerNameShouldNotNull() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"name\": null, \"password\": null}")
        .when().post("/customers")
        .then()
        .statusCode(400);
  }

  @Test
  void customerNameShouldNotBlank() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"name\": \"\", \"password\": \"\"}")
        .when().post("/customers")
        .then()
        .statusCode(400);
  }

}