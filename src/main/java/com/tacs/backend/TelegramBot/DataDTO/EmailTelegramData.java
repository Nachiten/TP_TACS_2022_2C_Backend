package com.tacs.backend.TelegramBot.DataDTO;

public class EmailTelegramData implements TelegramData_I {


    String email;
    String question;
    public  EmailTelegramData(String text){
        this.question = text;
    }
    public Boolean saveIfOK(String email) {
        return true;
    }


    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getcontents(){
        return this.email;

    }
}
