package com.tacs.backend.TelegramBot.functions;

import com.tacs.backend.TelegramBot.CORE.UsersSessions;
import com.tacs.backend.model.Match;
import com.tacs.backend.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
public class TelegramMatchDetail implements TelegramMessage {

    long userID = 0;
    String responseMessage = "";

    MatchService matchService = new MatchService();
    @Override
    public long getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(long userID) {
        this.userID = userID;
    }


    @Override
    public TelegramMessage sendText(String text, long id, UsersSessions usersSessions) {
        Match m = null;


        System.out.println("texto ingresado: " + text);
        try {
            // se envia la respuesta al ciiente con el resultado y se cierra

            System.out.println("aca");
            if(matchService == null) System.out.println("si");
            m = matchService.getMatch(text);


            System.out.println("recibido" + m.getLocation() + m.getId());

            // faltan agregar los jugadores del partido
            this.responseMessage = m.getLocation() + m.getStartingDateTime().toString();


        } catch(Exception e){
            this.responseMessage = "error con el id, por favor intenta de nuevo";
            usersSessions.deleteFinishSesion(this);


        } finally {

            usersSessions.deleteFinishSesion(this);

        }

        return this;
    }

    @Override
    public String responseMessage() {
        return responseMessage;
    }

    @Override
    public void setNextMessage() { this.responseMessage= "por favor ingrese el id del partido respectivo"; }

    @Override
    public void setMessage(String msg) {
        this.responseMessage = msg;
    }

}
