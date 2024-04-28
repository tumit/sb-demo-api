package id.thedev.tumit.sbdemoapi.cashcard;

import java.security.Principal;
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
    private ResponseEntity<Void> createCashCard(
            @RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        var newCashCardRequestWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        var savedCashCard = cashCardRepository.save(newCashCardRequestWithOwner);
        var location =
                ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(location).build();
    }
}
