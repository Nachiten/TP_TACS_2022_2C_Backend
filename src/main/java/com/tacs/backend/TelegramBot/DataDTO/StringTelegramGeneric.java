package com.tacs.backend.TelegramBot.DataDTO;


public class StringTelegramGeneric implements TelegramData_I {

    private String question;

    private String message;

    public StringTelegramGeneric(String msg){
        this.question = msg;
        this.message = null;
    }

    public Boolean saveIfOK(String msg){
        // se puede agrear mas logica para checkear si lo que se recibe es correcto para guardar
        this.message = msg.trim();
        return true;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getcontents() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

