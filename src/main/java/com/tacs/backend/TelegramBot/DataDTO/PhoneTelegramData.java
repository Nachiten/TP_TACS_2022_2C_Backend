package com.tacs.backend.TelegramBot.DataDTO;

public class PhoneTelegramData implements TelegramData_I {


    long phone;
    String question;

    public PhoneTelegramData(String text){
        this.question = text;
    }
    public Boolean saveIfOK(String phone){
        try {
            this.phone = Long.parseLong(phone.trim());
            return true;
        } catch(Exception e){
            return false;
        }

    }


    public String getQuestion() {
        return this.question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public long getcontents(){
        return this.phone;

    }
}
