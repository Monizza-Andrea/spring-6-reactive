package guru.springframework.spring_6_reactive.controllers;

import guru.springframework.spring_6_reactive.domain.Customer;
import guru.springframework.spring_6_reactive.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
public class CustomerControllerTest {

    private final String CUSTOMER_PATH_ID = "/api/v2/customer";
    private final String BASE_PATH = "http://localhost:8080";

    @Autowired
    WebTestClient webTestClient;



    @Order(1)
    @Test
    void testGetCustomerList() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(3);
    }


    @Order(2)
    @Test
    void testGetCustomerById() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDTO.class);
    }


    @Order(3)
    @Test
    void testSaveNewCustomer() {
        webTestClient.post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(BASE_PATH + CUSTOMER_PATH_ID + "/4");
    }

    @Order(4)
    @Test
    void testUpdateCustomer() {
        webTestClient.put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }


    @Order(99)
    @Test
    void testDeleteCustomer() {

        webTestClient.delete().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    CustomerDTO getCustomerDto() {
        return CustomerDTO.builder()
                .customerName("Fede")
                .email("fede@gmail.com")
                .build();
    }

}