package com.tacs.backend.TelegramBot.DataDTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTelegramData implements TelegramData_I {
    String question;
    private String date;

    public DateTelegramData(String msg){
        this.question = msg;
        this.date = null;
    }



    public Boolean saveIfOK(String date){
        Date convertedDate= null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            //System.out.println(date);
            formatter.parse(date);
            this.date = date.trim();
            return true;
        }catch(Exception error){
            System.out.println(error);
            return false;
        }

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getcontents() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


