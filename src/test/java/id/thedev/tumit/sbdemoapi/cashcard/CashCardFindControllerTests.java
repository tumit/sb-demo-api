package id.thedev.tumit.sbdemoapi.cashcard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashCardFindControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
        // * arrange
        // * act
        var res = restTemplate.getForEntity("/cashcards/99", String.class);

        // * assert
        assertThat(res.getStatusCode()).isEqualTo(OK);

        var documentContext = JsonPath.parse(res.getBody());
        var id = documentContext.read("$.id");
        assertThat(id).isEqualTo(99);

        var amount = documentContext.read("$.amount");
        assertThat(amount).isEqualTo(123.45);
    }

    @Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        // * arrange
        // * act
        var res = restTemplate.getForEntity("/cashcards/1000", String.class);
        // * assert
        assertThat(res.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(res.getBody()).isBlank();
    }

    // TODO: case 401 Unauthorized
}
