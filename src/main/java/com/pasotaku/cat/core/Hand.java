package com.pasotaku.cat.core;

import java.util.ArrayList;
import java.util.List;

import com.pasotaku.cardtype.CardTypes;

public class Hand {
    List<CardTypes> hand = new ArrayList<CardTypes>();

    public List<CardTypes> getHand() {
        return hand;
    }

    public void setHand(List<CardTypes> hand) {
        this.hand = hand;
    }
    
}
