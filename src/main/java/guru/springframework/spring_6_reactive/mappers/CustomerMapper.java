package guru.springframework.spring_6_reactive.mappers;

import guru.springframework.spring_6_reactive.domain.Customer;
import guru.springframework.spring_6_reactive.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
