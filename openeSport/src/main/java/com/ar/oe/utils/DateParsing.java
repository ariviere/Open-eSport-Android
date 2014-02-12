package com.ar.oe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateParsing {

	public String getSeconds(String dateSent){
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            isoFormat.setTimeZone(TimeZone.getDefault());
            Date date = isoFormat.parse(dateSent);
            Date currentDate = Calendar.getInstance().getTime();
            long l1 = date.getTime();
            long l2 = currentDate.getTime();
            long secondElapsed = l2-l1;
            return String.valueOf(secondElapsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

	public String timeFromPresent(String seconds){
		Long secondElapsed = Long.valueOf(seconds);
		String returnedDate;
		
		if(secondElapsed < 60000 && secondElapsed > -60000){
    		returnedDate = String.format("%d secs", TimeUnit.MILLISECONDS.toSeconds(secondElapsed));
    	}
    	else if(secondElapsed < 3600000 && secondElapsed > -3600000){
    		long minutes = TimeUnit.MILLISECONDS.toSeconds(secondElapsed);
    		minutes/=60;
    		returnedDate = String.format("%d mins", minutes);
    	}
    	else if(secondElapsed < 86400000 && secondElapsed > -86400000){
    		long heures = TimeUnit.MILLISECONDS.toSeconds(secondElapsed);
    		heures/=3600;
    		returnedDate = String.format("%d H", heures);
    	}
    	else{
    		long jours = TimeUnit.MILLISECONDS.toSeconds(secondElapsed);
    		jours/=86400;
    		returnedDate = String.format("%d J", jours);
    	}
		
		return returnedDate;
	}
}
