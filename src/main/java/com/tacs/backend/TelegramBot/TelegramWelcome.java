package com.tacs.backend.TelegramBot;

public class TelegramWelcome implements TelegramMessage{
    long userID = 0;
    String responseMessage = "";



    @Override
    public long getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(long userID) {
        this.userID= userID;
    }


    @Override
    public TelegramMessage sendText(String text, long userID,UsersSessions usersSessions) {


       if("1".equals(text)) {
        // crear partido
        TelegramMessage tm = new TelegramCreateMatch();

        tm.setUserID(userID);

        // finalizo la opcion actual y se genera la elegida por el usuario
        usersSessions.upSesion(this,tm);
        //usersSessions.deliverMessage(text,userID);
        return tm;
       } else if ("2".equals(text)) {
           // ver los detalles de un partido
           TelegramMessage tm = new TelegramMatchDetail();
           tm.setUserID(userID);
           // finalizo la opcion actual y se genera la elegida por el usuario
           usersSessions.upSesion(this,tm);
           tm.setNextMessage();
           //usersSessions.deliverMessage(text,userID);
            return tm;

       } else if ("3".equals(text)) {
           // salir
       }else {
           this.responseMessage = "opcion incorrecta escriba nuevamente";
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
       this.responseMessage= "Bienvenido a la tacs-app que desea realizar? : 1) crear un partido 2) anotarse a un partido 3) salir";
    }

    @Override
    public void setMessage(String msg) {
        this.responseMessage = msg;
    }


}
