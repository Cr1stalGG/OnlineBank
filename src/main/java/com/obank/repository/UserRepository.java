package com.obank.repository;

import com.obank.entity.Card;
import com.obank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getReferenceByUsername(String username);
    User getUserByCard(Card card);

}
