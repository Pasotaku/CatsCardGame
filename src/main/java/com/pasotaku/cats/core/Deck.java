package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Deck {

    Stack<CardTypes> deck = new Stack<>();

    public Deck(List<CardTypes> deckOrder) {
        for(CardTypes card: deckOrder) {
            deck.push(card);
        }
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
    
    //TODO: Implement shuffle
    //TODO: Add a way to intiialize the deck
    
    
}
