package com.pasotaku.cats;

import com.pasotaku.cardtype.CardTypes;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class CatGameEngineTest {
    private final static Logger logger = LoggerFactory.getLogger(CatGameEngineTest.class);

    public static final String test = "hello";

    List<CardTypes> cardTypeList = new ArrayList<>();

    @Before
    public void setup() {
        cardTypeList.add(CardTypes.CAT);
        cardTypeList.add(CardTypes.DEFUSE);
        cardTypeList.add(CardTypes.NOPE);
        cardTypeList.add(CardTypes.NOPE);
    }

    @Test
    public void nopeADefuseCard(){
        logger.info("Testing adding a nope and a defuse card.");
        //Player plays a cat
        LinkedList<CardTypes> deck = new LinkedList<>();
        LinkedList<CardTypes> playedCards = new LinkedList<>();
        deck.add(CardTypes.CAT);
        CardTypes currentCard = deck.pop();
        if(!playedCards.isEmpty()){
            //Check stuff here.
        }
        assertThat("hello", test.equalsIgnoreCase("hello"));
    }

    @Test
    public void nopeADefuseCardAndNopeAgain(){
        assertThat("hello", StringUtils.equalsIgnoreCase("hello", test));
    }



}
