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

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> updateCashCard(
            @PathVariable Long requestedId, @RequestBody CashCard updateCashCard, Principal principal) {

        var cardCashOpt = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());

        if (cardCashOpt.isPresent()) {
            var updatedCashCard = new CashCard(cardCashOpt.get().id(), updateCashCard.amount(), principal.getName());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

        //                return cashCardRepository.findByIdAndOwner(requestedId, principal.getName()).map(cashCard -> {
        //                    return ResponseEntity.noContent().build();
        //                }).orElseGet(() -> ResponseEntity.notFound().build());

        //        return cashCardRepository
        //                .findByIdAndOwner(requestedId, principal.getName())
        //                .map(cashCard -> {
        //                    var updatedCashCard = new CashCard(cashCard.id(), updateCashCard.amount(),
        // principal.getName());
        //                    cashCardRepository.save(updatedCashCard);
        //                    return ResponseEntity.noContent().build();
        //                })
        //                .orElse(ResponseEntity.notFound().build());
        //        var cardCashOpt = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
        //
        //        return cardCashOpt.map(cardCash -> {
        //            var updatedCashCard = new CashCard(cardCash.id(), updateCashCard.amount(), principal.getName());
        //            cashCardRepository.save(updatedCashCard);
        //            return ResponseEntity.noContent().build();
        //        }).orElse(ResponseEntity.notFound().build());
    }
}
