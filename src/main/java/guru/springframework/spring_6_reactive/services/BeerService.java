package guru.springframework.spring_6_reactive.services;

import guru.springframework.spring_6_reactive.model.BeerDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.FileChannel;

public interface BeerService {

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Integer beerId);

    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO);

    Mono<Void> deleteBeer(Integer beerId);
}
