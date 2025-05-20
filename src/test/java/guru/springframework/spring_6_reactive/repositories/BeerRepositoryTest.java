package guru.springframework.spring_6_reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring_6_reactive.config.DatabaseConfig;
import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.mappers.BeerMapper;
import guru.springframework.spring_6_reactive.mappers.BeerMapperImpl;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataR2dbcTest
@Import({DatabaseConfig.class, BeerMapperImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;


    @Test
    void testCreateJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(getTestBeer()));
    }

    @Test
    void saveNewBeer() {

        beerRepository.save(getTestBeer())
                .subscribe(beer -> {
                    System.out.println(beer.toString());
                });

    }

    @Test
    void testMapper_BeerToBeerDTO() {
        BeerDTO beerDTO = getTestBeerDTO();
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);

        assertEquals(beerDTO.getBeerName(), beer.getBeerName());
        assertEquals(beerDTO.getId(), beer.getId());
        assertEquals(beerDTO.getId(), beer.getId());
        assertEquals(beerDTO.getBeerStyle(), beer.getBeerStyle());
    }


    @Test
    void testMapper_BeerDTOToBeer() {
        Beer beer = getTestBeer();
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

        assertEquals(beerDTO.getBeerName(), beer.getBeerName());
        assertEquals(beerDTO.getId(), beer.getId());
        assertEquals(beerDTO.getId(), beer.getId());
        assertEquals(beerDTO.getBeerStyle(), beer.getBeerStyle());
    }

    Beer getTestBeer(){
        return Beer.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123213")
                .build();
    }

    BeerDTO getTestBeerDTO(){
        return BeerDTO.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .upc("123213")
                .build();
    }
}