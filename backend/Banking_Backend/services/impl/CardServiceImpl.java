package com.banking.backend.Banking_Backend.services.impl;

import com.banking.backend.Banking_Backend.dto.CardDTO;
import com.banking.backend.Banking_Backend.entites.Cards;
import com.banking.backend.Banking_Backend.entites.User;
import com.banking.backend.Banking_Backend.repository.CardsRepository;
import com.banking.backend.Banking_Backend.repository.UserRepository;
import com.banking.backend.Banking_Backend.request.CardRequest;
import com.banking.backend.Banking_Backend.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardsRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    public CardDTO issueCard(CardRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cards card = new Cards();
        card.setCardNumber(request.getCardNumber());
        card.setCardType(request.getCardType());
        card.setIssuseDate(request.getIssueDate());
        card.setExpiryDate(request.getExpiryDate());
        card.setUser(user);

        return toDTO(cardRepository.save(card));
    }

    @Override
    public List<CardDTO> getCardsByUserId(Long userId) {
        return cardRepository.findById(userId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CardDTO toDTO(Cards card) {
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setCardType(card.getCardType());
        dto.setIssueDate(card.getIssuseDate());
        dto.setExpiryDate(card.getExpiryDate());
        dto.setUserId(card.getUser().getUserId());
        return dto;
    }
}
