package com.tacs.backend.TelegramBot.DataDTO;

import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeConverter {



    public LocalDateTime convertToDate(String date, String time){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {

            return LocalDateTime.parse(date + " " + time,formatter);


        }catch(Exception error){
            System.out.println(error);
            return null;
        }



    }


}
