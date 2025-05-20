package guru.springframework.spring_6_reactive.services;

import guru.springframework.spring_6_reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface CustomerService {
    Mono<Void> deleteCustomer(Integer customerId);

    Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> getCustomerById(Integer customerId);

    Flux<CustomerDTO> listCustomers();
}
