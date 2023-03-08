package com.obank.service;

import com.obank.entity.Card;
import com.obank.entity.User;
import com.obank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService  {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateCardNumber(Long id, String cardNumber){
        User user = userRepository.getReferenceById(id);

        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setAmount(BigDecimal.valueOf(0.0));

        user.setCard(card);
        userRepository.save(user);
    }

    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    public void save(User user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setCard(new Card());
        user.setRoles("USER");

        userRepository.save(user);
    }

}
