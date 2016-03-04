package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Deck {

    Stack<CardTypes> deck = new Stack<>();

    public Deck(){

    }

    public Deck(List<CardTypes> deckOrder) {
        for(CardTypes card: deckOrder) {
            deck.push(card);
        }
    }
    public Stack<CardTypes> getDeck(){
        return this.deck;
    }

    private void setDeck(Stack<CardTypes> deck){
        this.deck = deck;
    }

    public CardTypes drawCard() {
        if(!deck.isEmpty()) {
            return deck.pop();
        }
        return null;
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public void placeCardOnTop(CardTypes card) {
        if(card!= null){
            deck.push(card);
        }
    }

    public void placeCardAtLocation(CardTypes card, int location) {
        if(card!= null) {
            List<CardTypes> tempList = new ArrayList<>();
            for (int i = 0; i < location; i++) {
                tempList.add(deck.pop());
            }
            deck.push(card);
            for (CardTypes cards : tempList) {
                deck.push(cards);
            }
        }
    }

    public int size(){
        return deck.size();
    }

    public CardTypes peek(){
        return deck.peek();
    }

    public void shuffle(){
        List<CardTypes> unsortedCards = new ArrayList<>();
        int numberOfCards = size();
        List<Integer> index = new ArrayList<>();
        while(!isEmpty()){
            unsortedCards.add(drawCard());
        }
        Stack<CardTypes> updatedDeck = new Stack<>();


        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0;i <numberOfCards;i++ ) {
            int cardToGrab = secureRandom.nextInt(numberOfCards);
            while(index.contains(cardToGrab)) {
                cardToGrab = secureRandom.nextInt(numberOfCards);
            }
            CardTypes card = unsortedCards.get(cardToGrab);
            index.add(cardToGrab);
            updatedDeck.push(card);
        }
        setDeck(updatedDeck);
    }
}
