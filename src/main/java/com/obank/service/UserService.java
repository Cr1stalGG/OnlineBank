package com.obank.service;

import com.obank.entity.Card;
import com.obank.entity.User;
import com.obank.repository.CardRepository;
import com.obank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService  {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public UserService(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public void updateCardNumber(Long id, String cardNumber){
        User user = userRepository.getReferenceById(id);

        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setAmount(BigDecimal.valueOf(0.0));

        userRepository.delete(userRepository.getReferenceById(id));

        user.setId(id);
        user.setCard(card);

        userRepository.save(user);
    }

}
