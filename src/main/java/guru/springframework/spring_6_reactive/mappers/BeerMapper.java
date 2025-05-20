package guru.springframework.spring_6_reactive.mappers;

import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer (BeerDTO dto);
    BeerDTO beerToBeerDto (Beer beer);
}
