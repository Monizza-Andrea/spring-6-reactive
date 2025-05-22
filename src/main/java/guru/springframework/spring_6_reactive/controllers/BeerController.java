package guru.springframework.spring_6_reactive.controllers;


import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;


    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteExistingBeer(@PathVariable("beerId") Integer beerId) {

        return beerService.deleteBeer(beerId)
                .thenReturn(ResponseEntity.noContent().build());
    }


    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingBeer(@PathVariable("beerId") Integer beerId,
                                                 @Validated @RequestBody BeerDTO beerDTO) {

        return beerService.patchBeer(beerId, beerDTO)
                .map(savedDTO -> ResponseEntity.created(UriComponentsBuilder
                                .fromUriString("http://localhost:8080/" + BEER_PATH + "/" + savedDTO.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> updateExistingBeer(@PathVariable("beerId") Integer beerId,
                                                  @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.updateBeer(beerId,beerDTO)
                .map(savedDTO -> ResponseEntity.noContent().build());
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO) {

        return beerService.saveNewBeer(beerDTO)
                .map(savedDTO -> ResponseEntity.created(UriComponentsBuilder
                        .fromUriString("http://localhost:8080/" + BEER_PATH + "/" + savedDTO.getId())
                        .build().toUri())
                        .build());
    }

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId){
        return beerService.getBeerById(beerId);
    }

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }


}
