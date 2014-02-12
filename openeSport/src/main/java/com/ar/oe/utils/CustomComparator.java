package com.ar.oe.utils;

import java.util.Comparator;

import com.ar.oe.classes.Post;

public class CustomComparator implements Comparator<Post> {

    DateParsing dp = new DateParsing();

	@Override
    public int compare(Post o1, Post o2) {
        long longNumber = (Long.parseLong(dp.getSeconds(o1.getPubDate()))) - (Long.parseLong(dp.getSeconds(o2.getPubDate())));
        if(longNumber < 0)
            return -1;
        if(longNumber > 0)
            return 1;
        else return 0;
    }
}
