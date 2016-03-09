package com.pasotaku.cats;

import com.pasotaku.cardtype.CardTypes;
import com.pasotaku.cats.core.CatGameEngine;
import com.pasotaku.cats.core.Deck;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CatGameEngineTest {
    private final static Logger logger = LoggerFactory.getLogger(CatGameEngineTest.class);

    public final String helloString = "hello";

    List<CardTypes> cardTypeList = new ArrayList<>();
    Deck deck;
    CatGameEngine catGameEngine = new CatGameEngine();

    @Before
    public void setup() {
        cardTypeList.add(CardTypes.CAT);
        cardTypeList.add(CardTypes.UNCAT);
        cardTypeList.add(CardTypes.NOCATNO);
        cardTypeList.add(CardTypes.NOCATNO);
        deck = new Deck(cardTypeList);
    }

    @Test
    public void uncatANOCATNOCard() {
        assertThat(CardTypes.UNCAT, equalTo(catGameEngine.victor(CardTypes.CAT, CardTypes.UNCAT)));
    }

    @Test
    public void nopeADefuseCardAndNopeAgain(){
        assertThat("hello", StringUtils.equalsIgnoreCase("hello", helloString));
    }



}
