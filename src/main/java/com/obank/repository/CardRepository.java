package com.obank.repository;

import com.obank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card getReferenceByCardNumber(String catdNumber);
}
