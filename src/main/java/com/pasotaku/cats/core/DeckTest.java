package com.pasotaku.cats.core;

import com.pasotaku.cardtype.CardTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;

public class DeckTest {
    Deck deck;

    @Before
    public void setup() {
        List<CardTypes> tempDeck = new ArrayList<>();
        tempDeck.add(CardTypes.UNCAT);
        tempDeck.add(CardTypes.NORMAL_CARD);
        tempDeck.add(CardTypes.NORMAL_CARD);
        tempDeck.add(CardTypes.FAVOR);
        tempDeck.add(CardTypes.NORMAL_CARD);
        tempDeck.add(CardTypes.NORMAL_CARD);
        tempDeck.add(CardTypes.NOCATNO);
        tempDeck.add(CardTypes.CAT);
        deck = new Deck(tempDeck);
    }

    @Test
    public void retrieveCat(){
        CardTypes card = deck.drawCard();
        assertThat(card, equalTo(CardTypes.CAT));
    }

    @Test
    public void retriveNope(){
        CardTypes card = deck.drawCard();
        card = deck.drawCard();
        assertThat(card, equalTo(CardTypes.NOCATNO));
    }

    @Test
    public void getKittenPlaceBack(){
        CardTypes card = deck.drawCard();
        deck.placeCardOnTop(card);
        assertThat(deck.peek(), equalTo(CardTypes.CAT));
    }

    @Test
    public void getKittenPlaceThreeBack(){
        CardTypes card = deck.drawCard();
        deck.placeCardAtLocation(card,3);
        assertThat(deck.peek(),not(CardTypes.CAT));
        deck.drawCard();
        deck.drawCard();
        deck.drawCard();
        assertThat(deck.peek(), equalTo(CardTypes.CAT));
    }

    @Test
    public void shuffleDeck(){
        Stack<CardTypes> originalDeck = (Stack<CardTypes>) deck.getDeck().clone();
        deck.shuffle();
        Stack<CardTypes> shuffleDeck = deck.getDeck();
        assertFalse("original deck isn't the same as the shuffled deck",originalDeck.equals(shuffleDeck) );

    }


}
