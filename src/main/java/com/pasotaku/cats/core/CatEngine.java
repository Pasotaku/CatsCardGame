package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;

public class CatEngine {

    //Keep track of player's hands.
    //Keep track of deck
    //Keep track of playedCards
    //Keep track of discardPile


    public CardTypes victor(CardTypes card, CardTypes secondCard) {
        if(card.isNormalCard()) {
            return secondCard;
        }
        if(card.getCardType().equals("cat-boom")){
            if(secondCard.getCardType().equals("uncat")){
                return secondCard;
            }
        }
        if(secondCard.getCardType().equals("no-cat-no")){
            return secondCard;
        }
        return null;
    }
}
