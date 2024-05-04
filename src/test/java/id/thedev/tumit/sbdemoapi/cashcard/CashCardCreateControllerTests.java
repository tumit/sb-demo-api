package id.thedev.tumit.sbdemoapi.cashcard;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CashCardCreateControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DirtiesContext
    void shouldCreateANewCashCard() {
        var newCashCard = new CashCard(null, 250.00, null);
        var createResponse =
                restTemplate.withBasicAuth("sarah1", "123").postForEntity("/cashcards", newCashCard, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var locationOfNewCashCard = createResponse.getHeaders().getLocation();
        var getResponse = restTemplate.withBasicAuth("sarah1", "123").getForEntity(locationOfNewCashCard, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Add assertions such as these
        var documentContext = JsonPath.parse(getResponse.getBody());
        var id = documentContext.read("$.id");
        var amount = documentContext.read("$.amount");
        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(250.00);
    }

    @Test
    void shouldUpdateAnExistingCashCard() {
        var cashCardUpdate = new CashCard(null, 19.99, null);
        var req = new HttpEntity<>(cashCardUpdate);

        // not use put because we need response
        var res =
                restTemplate.withBasicAuth("sarah1", "123").exchange("/cashcards/99", HttpMethod.PUT, req, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var getRes = restTemplate.withBasicAuth("sarah1", "123").getForEntity("/cashcards/99", String.class);
        assertThat(getRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        var documentContext = JsonPath.parse(getRes.getBody());
        var id = documentContext.read("$.id");
        var amount = documentContext.read("$.amount");
        assertThat(id).isEqualTo(99);
        assertThat(amount).isEqualTo(19.99);
    }

    @Test
    void shouldNotUpdateACashCardThatDoesNotExist() {
        // * arrange
        var unknownCashCard = new CashCard(null, 19.99, null);
        var req = new HttpEntity<>(unknownCashCard);

        // * act
        var res = restTemplate
                .withBasicAuth("sarah1", "123")
                .exchange("/cashcards/1000", HttpMethod.PUT, req, Void.class);

        // * assert
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotUpdateACashCardThatIsOwnedByAnotherUser() {
        // * arrange
        var kumarsCard = new CashCard(null, 333.33, null);
        var req = new HttpEntity<>(kumarsCard);

        // * act
        var res =
                restTemplate.withBasicAuth("sarah1", "123").exchange("/cashcards/102", HttpMethod.PUT, req, Void.class);
        // * assert
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
