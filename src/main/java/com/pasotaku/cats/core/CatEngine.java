package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;

import java.util.ArrayList;
import java.util.List;

public class CatEngine {

    //Keep track of player's hands.
    //Keep track of deck
    //Keep track of playedCards
    //Intiialize the Cats Deck


    List<Hand> playerHands = new ArrayList<>();
    Deck deck;
    CardTypes currentCard;
    int numberOfPlayers;

    public void setNumberOfPlayers(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    public void initializeDeck(){
        List<CardTypes> deckList = new ArrayList();

    }


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
