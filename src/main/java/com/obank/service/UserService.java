package com.obank.service;

import com.obank.entity.Card;
import com.obank.entity.Transaction;
import com.obank.entity.User;
import com.obank.repository.CardRepository;
import com.obank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        user.setCard(card);
        userRepository.save(user);
    }
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public User getAuthUser(){
        return getUserByUsername(getCurrentUsername());
    }

    public User getUserByUsername(String username){
        return userRepository.getReferenceByUsername(username);
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

    public void addTransaction(Long fromUserId, String toCardNumber, BigDecimal amount){
        User user = userRepository.getReferenceById(fromUserId);
        Card toCard = cardRepository.getReferenceByCardNumber(toCardNumber);

        if (user.getCard().getAmount().compareTo(amount) > 0 && user.getCard().getCardNumber() != null) {
            Transaction transaction = new Transaction();
            transaction.setFrom(user.getCard());
            transaction.setTo(toCard);
            transaction.setAmount(amount);

            user.setTransaction(transaction);

            user.getCard().setAmount(user.getCard().getAmount().subtract(amount));
            toCard.setAmount(toCard.getAmount().add(amount));

            userRepository.save(user);
            cardRepository.save(toCard);
        }
    }

    public void addAmount(Long userId, BigDecimal amount){
        User user = userRepository.getReferenceById(userId);

        if(amount.compareTo(BigDecimal.valueOf(0)) > 0 && user.getCard().getCardNumber() != null)
            user.getCard().setAmount(user.getCard().getAmount().add(amount));

        userRepository.save(user);
    }

    public void getAmount(Long userId, BigDecimal amount){
        User user = userRepository.getReferenceById(userId);

        if(user.getCard().getAmount().compareTo(amount) >= 0 && user.getCard().getCardNumber() != null)
            user.getCard().setAmount(user.getCard().getAmount().subtract(amount));

        userRepository.save(user);
    }

}
