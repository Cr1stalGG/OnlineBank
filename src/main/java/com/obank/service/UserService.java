package com.obank.service;

import com.obank.entity.User;
import com.obank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateCardNumber(Long id, String cardNumber){
        User user = userRepository.getReferenceById(id);

        userRepository.delete(userRepository.getReferenceById(id));

        user.setId(id);
        user.setCardNumber(cardNumber);

        userRepository.save(user);
    }

}
