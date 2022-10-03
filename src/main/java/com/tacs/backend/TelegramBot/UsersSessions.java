package com.tacs.backend.TelegramBot;


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
        //System.out.println("el id antes de procesar es: " + objMsg.get().responseMessage());
        var response = objMsg.get().sendText(text,idUser, this);
        //System.out.println("cantidad de opciones por id:   ");
        //System.out.println(messageTextReceived.stream().count());

        //System.out.println(response.responseMessage());
        // borrar los mensajes terminados que se hayan terminado
        //this.deleteFinishSesion();//se borran las sesiones si estan finalizadas
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
        this.messageTextReceived.remove(tm);
    }

        // levante una sesion con la opcion  del primer parametro por la segunda
    public void upSesion(TelegramMessage t, TelegramMessage m) {
        this.messageTextReceived.remove(t);
        this.messageTextReceived.add(m);
    }
}
