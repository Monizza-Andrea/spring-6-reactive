package guru.springframework.spring_6_reactive.controllers;

import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.repositories.BeerRepository;
import guru.springframework.spring_6_reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    private final String BEER_PATH_ID = "/api/v2/beer";
    private final String BASE_PATH = "http://localhost:8080";

    @Autowired
    WebTestClient webTestClient;


    @Order(1)
    @Test
    void testListBeers() {
        webTestClient.get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Order(2)
    @Test
    void testGetBeerById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID, "2")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Order(3)
    @Test
    void testSaveNewBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeerDTO()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(BASE_PATH + BEER_PATH_ID + "/4");
    }

    @Order(4)
    @Test
    void testUpdateBeer() {

        webTestClient.put().uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(BeerRepositoryTest.getTestBeerDTO()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Order(99)
    @Test
    void testDeleteBeer() {

        webTestClient.delete().uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }


}