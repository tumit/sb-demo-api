package id.thedev.tumit.sbdemoapi.cashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
record CashCard(@Id Long id, Double amount, String owner) {}
