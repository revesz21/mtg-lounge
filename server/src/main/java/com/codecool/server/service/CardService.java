package com.codecool.server.service;

import com.codecool.server.dto.CardDTO;
import com.codecool.server.model.Card;
import com.codecool.server.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Transactional(readOnly = true)
    public List<CardDTO> getAllCards() {
        return cardRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<CardDTO> getCardsByManaCost(String manaCost) {
        List<Card> cards = cardRepository.findByManaCost(manaCost);
        return cards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CardDTO> getCardsByNameContaining(String name) {
        List<Card> cards = cardRepository.findByNameContainingIgnoreCase(name);
        return cards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CardDTO convertToDTO(Card card) {
        return new CardDTO(
                card.getId(),
                card.getName(),
                card.getManaCost(),
                card.getOracleText(),
                card.getSet(),
                card.getImageUris()
        );
    }

}