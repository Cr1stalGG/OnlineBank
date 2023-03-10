package com.obank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "CARDS")
@Data
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "CARD_NUMBER")
    private String  cardNumber = generateCardNumber();
    @Column(name = "AMOUNT")
    private BigDecimal amount = BigDecimal.valueOf(0);

    public Card() {
    }
    private String generateCardNumber(){
        final int from = 1000;
        final int to = 9999;
        StringBuffer cardNumber = new StringBuffer("");

        for(int i = 0; i < 4; ++i){
            cardNumber.append(getRandomNumberUsingNextInt(from, to));
        }
        return String.valueOf(cardNumber);
    }
    private int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!Objects.equals(id, card.id)) return false;
        if (!Objects.equals(cardNumber, card.cardNumber)) return false;
        return Objects.equals(amount, card.amount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
