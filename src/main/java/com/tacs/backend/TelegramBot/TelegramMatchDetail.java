package com.tacs.backend.TelegramBot;

public class TelegramMatchDetail implements TelegramMessage{

    long userID = 0;
    String responseMessage = "por favor ingrese el id del partido respectivo";
    @Override
    public long getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(long userID) {
        this.userID= userID;
    }

    @Override
    public TelegramMessage sendText(String text, long id, UsersSessions usersSessions) {
        // aca va la peticion http
        if(true) { // falta corroborar si la info es correcta y hacer la peticion http
            //TelegramMessage tm = new TelegramWelcome();
            //tm.setUserID(userID);
            // finalizo la opcion actual y se genera la elegida por el usuario
            usersSessions.deleteFinishSesion(this);
            return this;
        }

        return this;
    }

    @Override
    public String responseMessage() {
        return responseMessage;
    }

    @Override
    public void setNextMessage() {

    }

    @Override
    public void setMessage(String msg) {
        this.responseMessage = msg;
    }

    public void getNextMessage(){
        this.responseMessage = "por favor ingrese el id del partido respectivo";
    }
}
