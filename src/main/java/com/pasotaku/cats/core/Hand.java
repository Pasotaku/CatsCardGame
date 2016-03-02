package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<CardTypes> hand = new ArrayList<CardTypes>();

    public List<CardTypes> getHand() {
        return hand;
    }

    public void setHand(List<CardTypes> hand) {
        this.hand = hand;
    }


    public CardTypes getCardFromPlayer(int index) {
        return getHand().get(index);
    }
}
