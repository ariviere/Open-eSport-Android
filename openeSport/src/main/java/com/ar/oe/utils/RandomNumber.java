package com.ar.oe.utils;

import java.util.Random;

/**
 * Created by ariviere on 10/22/13.
 */
public class RandomNumber {
    public int generateRandomNumber(){
        Random rn = new Random();
        int randomNum = rn.nextInt(5) + 1;
        return randomNum;
    }
}
