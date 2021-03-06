package com.pasotaku.cats;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.pasotaku.cardtype.CardTypes;

public class App 
{
    public static void main( String[] args ) {
        File file = new File("jimmykun.txt");
        try {
            List lines = FileUtils.readLines(file, "UTF-8");
            CardTypes normalCard = CardTypes.NORMAL_CARD;
            CardTypes nope = CardTypes.NOCATNO;
            System.out.println(normalCard);
            System.out.println(nope);
            System.out.println(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
