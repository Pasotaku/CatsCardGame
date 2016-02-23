package com.pasotaku.cat.core;

import java.util.ArrayList;
import java.util.List;

import com.pasotaku.cardtype.CardTypes;

public class Deck {

    List<CardTypes> deck = new ArrayList<CardTypes>(50);

    public List<CardTypes> getDeck() {
        return deck;
    }

    public void setDeck(List<CardTypes> deck) {
        this.deck = deck;
    }
    
    //TODO: Implement shuffle method
    
    
}
