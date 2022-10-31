package com.tacs.backend.TelegramBot.DataDTO;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeTelegramData implements TelegramData_I {

    private String question;

    private String time;

    public TimeTelegramData(String msg){
        this.question = msg;
        this.time = null;
    }

    public Boolean saveIfOK(String time){
        Date d = null;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        try {
            d = formatter.parse(time);
            this.time = time;
            return true;
        } catch(Exception error){
            System.out.println(error);
            //this.time = d;
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
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
