package com.tacs.backend.TelegramBot.CORE;


import com.tacs.backend.TelegramBot.functions.TelegramMessage;
import com.tacs.backend.TelegramBot.functions.TelegramWelcome;

import java.util.LinkedList;
import java.util.Optional;

public class UsersSessions {


    private LinkedList<TelegramMessage> messageTextReceived= new LinkedList<>();


    public boolean existsIdUser(long id){


        for(TelegramMessage elem : messageTextReceived ){


            if(elem.getUserID() == id) return true;

        }
        return false;
    }

    public Optional<TelegramMessage> searchSesionUser(long id){
        return  messageTextReceived.stream().filter(m -> m.getUserID() == id).findFirst();
    }

    public String deliverMessage(String text, long idUser){
        var objMsg = this.searchSesionUser(idUser);
        //System.out.println(text + "dasdasdsas"); //aca no llega el text

        //System.out.println(objMsg.get().sendText() + "existe el objeto");
        var response = objMsg.get().sendText(text,idUser, this);
        return response.responseMessage();
    }

    public String newSesion(long id, String text){
        TelegramWelcome m = new TelegramWelcome();
        m.setUserID(id);
        m.setNextMessage();
        // se levanta la sesion

        this.messageTextReceived.add(m);
        return m.responseMessage();
    }

    public void deleteFinishSesion(TelegramMessage tm) {
        // se envia la informacion al back. si hay un error se envia al menu principal mostrando un error y se cierra la session actual
        

        this.messageTextReceived.remove(tm);
    }

        // levante una sesion con la opcion  del primer parametro por la segunda
    public void upSesion(TelegramMessage t, TelegramMessage m) {
        this.messageTextReceived.remove(t);
        this.messageTextReceived.add(m);
    }
}
