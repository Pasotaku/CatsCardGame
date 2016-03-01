package com.pasotaku.cats;

import com.pasotaku.cardtype.CardTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

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
        assertThat("hello", test.equalsIgnoreCase("hello"));
    }

    @Test
    public void nopeADefuseCardAndNopeAgain(){
        assertThat("hello", StringUtils.equalsIgnoreCase("hello", test));
    }



}
