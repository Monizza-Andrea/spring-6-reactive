package guru.springframework.spring_6_reactive.repositories;

import guru.springframework.spring_6_reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository <Beer, Integer> {
}
