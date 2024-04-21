package id.thedev.tumit.sbdemoapi.cashcard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cashcards")
public class CashCardCreateController {

    private final CashCardRepository cashCardRepository;

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        var savedCashCard = cashCardRepository.save(newCashCardRequest);
        var location =
                ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(location).build();
    }
}
