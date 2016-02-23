package com.pasotaku.cat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class App 
{
    public static void main( String[] args ) {
        File file = new File("jimmykun.txt");
        try {
            List lines = FileUtils.readLines(file, "UTF-8");
            System.out.println(lines);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("hehehehehehehe");
    }
}
