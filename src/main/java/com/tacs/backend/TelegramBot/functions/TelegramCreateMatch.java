package com.tacs.backend.TelegramBot.functions;

import com.tacs.backend.TelegramBot.DataDTO.*;
import com.tacs.backend.TelegramBot.CORE.UsersSessions;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.service.MatchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TelegramCreateMatch implements TelegramMessage {

    long userID = 0;
    String responseMessage = "";
    int  index = 0;
    final int limit =2;
    final int date = 0;
    final int time = 1;
    final int location = 2;

    MatchService ms = new MatchService();

    Object[] buffer ={new DateTelegramData("ingrese la fecha con el formato dd/MM/yyyy:"),
                      new TimeTelegramData("ingrese la hora con el formato HH:mm:"),
                      new StringTelegramGeneric("ingrese el lugar")

    };

    @Override
    public long getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(long userID) {
            this.userID=userID;
    }

    @Override
    public TelegramMessage sendText(String text, long userID, UsersSessions usersSessions) {
        DateTimeConverter converter = new DateTimeConverter();

        if( index >= limit) {
            // se termina la sesion del id y se envia la info al back
            LocalDateTime aux = converter.convertToDate(
                    ((DateTelegramData) buffer[date]).getcontents(),
                    ((TimeTelegramData) buffer[time]).getcontents()
            );

            MatchCreationDTO m = new MatchCreationDTO();
            m.setLocation(((StringTelegramGeneric) buffer[location]).getcontents());
            m.setStartingDateTime(aux);

            Match result = ms.createMatch(m); //descomentar cuando se quiera conectar con el back

            this.responseMessage = "partido creado el id es " +result.getId() ;

            usersSessions.deleteFinishSesion(this);
        } else {
            if(index <= limit && ( (TelegramData_I) buffer[index]).saveIfOK(text)) {

                // se acepta la respuestas del cliente y se pide el proximo dato
                index += 1;

                this.responseMessage = ((TelegramData_I) buffer[index]).getQuestion();
            } else {// error en la carga de la info. se envia de respuesta el mensaje de error
                this.responseMessage = "la fecha o la hora mal cargada, por favor escriba de nuevo con el formato requirido";
            }
        }


        return this;
    }

    @Override
    public String responseMessage() {


        return this.responseMessage;
    }


    public void setNextMessage(){
        this.responseMessage = ((TelegramData_I) buffer[index]).getQuestion();
    }

    @Override
    public void setMessage(String msg) {

    }

    public LocalDateTime joinDateTime(String lista){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
        LocalDateTime localDate = LocalDate.parse(lista,formatter).atStartOfDay();

        return localDate;

    }


}
