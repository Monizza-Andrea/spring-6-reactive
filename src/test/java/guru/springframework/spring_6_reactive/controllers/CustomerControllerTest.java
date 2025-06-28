package guru.springframework.spring_6_reactive.controllers;

import guru.springframework.spring_6_reactive.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;


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
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(3);
    }


    @Order(2)
    @Test
    void testGetCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDTO.class);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH_ID, 16)
                .exchange()
                .expectStatus().isNotFound();
    }



    @Test
    void testSaveNewCustomerBadData() {
        CustomerDTO testCustomer = getCustomerDto();
        testCustomer.setEmail("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(3)
    @Test
    void testSaveNewCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(BASE_PATH + CUSTOMER_PATH_ID + "/4");
    }

    @Test
    void testUpdateCustomerBadData() {
        CustomerDTO testCustomer = getCustomerDto();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateCustomerNotFound() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID, 16)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Order(4)
    @Test
    void testUpdateCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    void testPatchExistingCustomerBadData(){

        CustomerDTO testCustomer = getCustomerDto();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    void testPatchExistingCustomerNotFound(){

        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID, 16)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Order(5)
    @Test
    void testPatchExistingCustomer(){

        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getCustomerDto()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectHeader().location(BASE_PATH + CUSTOMER_PATH_ID + "/1");
    }



    @Test
    void testDeleteCustomerNotFound() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CustomerController.CUSTOMER_PATH_ID, 16)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Order(99)
    @Test
    void testDeleteCustomer() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    CustomerDTO getCustomerDto() {
        return CustomerDTO.builder()
                .customerName("Federico")
                .email("fede@gmail.com")
                .build();
    }

}