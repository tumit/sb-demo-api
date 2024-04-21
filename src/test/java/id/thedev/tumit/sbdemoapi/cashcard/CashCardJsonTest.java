package id.thedev.tumit.sbdemoapi.cashcard;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        var cashCard = new CashCard(99L, 123.45);
        var content = json.write(cashCard);
        assertThat(content).isStrictlyEqualToJson("expected.json");
        assertThat(content).hasJsonPathNumberValue("@.id");
        assertThat(content).extractingJsonPathNumberValue("@.id").isEqualTo(99);
        assertThat(content).hasJsonPathNumberValue("@.amount");
        assertThat(content).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }
}
