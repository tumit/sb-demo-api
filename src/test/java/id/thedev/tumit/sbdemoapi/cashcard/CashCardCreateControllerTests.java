package id.thedev.tumit.sbdemoapi.cashcard;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashCardCreateControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateANewCashCard() {
        var newCashCard = new CashCard(null, 250.00);
        var createResponse = restTemplate.postForEntity("/cashcards", newCashCard, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var locationOfNewCashCard = createResponse.getHeaders().getLocation();
        var getResponse = restTemplate.getForEntity(locationOfNewCashCard, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Add assertions such as these
        var documentContext = JsonPath.parse(getResponse.getBody());
        var id = documentContext.read("$.id");
        var amount = documentContext.read("$.amount");
        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(250.00);
    }
}
