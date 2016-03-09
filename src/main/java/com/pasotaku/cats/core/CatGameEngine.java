package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CatGameEngine {

    //Keep track of player's hands. DONE
    //Keep track of deck.
    //Keep track of playedCards
    //Intiialize the Cats Deck

    List<Hand> playerHands = new ArrayList<>();
    List<String> players = new ArrayList<>();

    Deck deck;
    CardTypes currentCard;
    Stack<CardTypes> playedCards = new Stack<>();
    int numberOfPlayers;


    public void setNumberOfPlayers(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    public void initializeDeck() {
        List<CardTypes> deckList = new ArrayList();
        //TODO: Determine the number of cards we should put in here.
        deck = new Deck(deckList);
    }


    public int getNumberOfCharacters(){
        return this.numberOfPlayers;
    }

    public List<Hand> getPlayerHands(){
        return this.playerHands;
    }

    public Hand getSpecifficPlayerHand(int number) {
        if(number >= getPlayerHands().size()) {
            return getPlayerHands().get(number);
        }
        return null; //Bad player number
    }

    public void addPlayer(String player) {
        if(!StringUtils.isEmpty(player)) {
            players.add(player);
        }
    }

    public List<String> getPlayers() {
        return this.players;
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
