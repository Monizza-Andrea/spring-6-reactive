package guru.springframework.spring_6_reactive.repositories;

import guru.springframework.spring_6_reactive.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository <Customer,Integer> {
}
