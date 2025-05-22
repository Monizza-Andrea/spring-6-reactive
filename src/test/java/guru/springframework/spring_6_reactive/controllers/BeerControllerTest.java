package guru.springframework.spring_6_reactive.controllers;

import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


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

    @Test
    void testGetBeerByIdNotFound() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID, "16")
                .exchange()
                .expectStatus().isNotFound();
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

    @Test
    void testSaveNewBeerBadData() {

        BeerDTO testBeer = BeerRepositoryTest.getTestBeerDTO();
        testBeer.setBeerName("");

        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateBeerBadData() {
        BeerDTO testBeer = BeerRepositoryTest.getTestBeerDTO();
        testBeer.setBeerStyle("");

        webTestClient.put().uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateBeerNotFound() {

        webTestClient.put().uri(BeerController.BEER_PATH_ID, 16)
                .body(Mono.just(BeerRepositoryTest.getTestBeerDTO()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
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


    @Test
    void testPatchExistingBeerBadData(){

        BeerDTO testBeer = BeerRepositoryTest.getTestBeerDTO();
        testBeer.setBeerName("");

        webTestClient.patch().uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    void testPatchExistingBeerNotFound(){

        webTestClient.patch().uri(BeerController.BEER_PATH_ID, 16)
                .body(Mono.just(BeerRepositoryTest.getTestBeerDTO()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }


    @Order(5)
    @Test
    void testPatchExistingBeer(){

        webTestClient.patch().uri(BeerController.BEER_PATH_ID, 1)
                .body(Mono.just(BeerRepositoryTest.getTestBeerDTO()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectHeader().location(BASE_PATH + BEER_PATH_ID + "/1");
    }



    @Test
    void testDeleteBeerNotFound() {

        webTestClient.delete().uri(BeerController.BEER_PATH_ID, 16)
                .exchange()
                .expectStatus()
                .isNotFound();
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