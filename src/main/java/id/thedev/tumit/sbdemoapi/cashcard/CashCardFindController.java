package id.thedev.tumit.sbdemoapi.cashcard;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cashcards")
public class CashCardFindController {

    private final CashCardRepository cashCardRepository;

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        return cashCardRepository
                .findByIdAndOwner(requestedId, principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    private ResponseEntity<Iterable<CashCard>> findAll(Pageable pageable, Principal principal) {
        var pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount")));
        var page = cashCardRepository.findByOwner(principal.getName(), pageRequest);
        return ResponseEntity.ok(page.getContent());
    }
}
