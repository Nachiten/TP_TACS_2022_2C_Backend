package com.tacs.backend.TelegramBot.functions;

import com.tacs.backend.TelegramBot.DataDTO.EmailTelegramData;
import com.tacs.backend.TelegramBot.DataDTO.PhoneTelegramData;
import com.tacs.backend.TelegramBot.DataDTO.StringTelegramGeneric;
import com.tacs.backend.TelegramBot.DataDTO.TelegramData_I;
import com.tacs.backend.TelegramBot.CORE.UsersSessions;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.model.Match;
import com.tacs.backend.service.MatchService;

public class TelegramJoinMatch implements TelegramMessage{
    String responseMessage = "";

    long userID = 0;

    boolean createPlayer = false;
    boolean readMatch = false;

    int index = 0;
    final int limit = 2;
    final int phone = 1;
    final int email = 2;

    boolean matchID = false; // se cambia de false a true por testing



    MatchService ms = new MatchService();
    @Override
    public long getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(long userID) {
        this.userID = userID;
    }



    Object[] buffer ={new StringTelegramGeneric("¡Por favor ingrese el id del partido a anotarse!"),
                      new PhoneTelegramData("Ingrese el numero de telefono"),
                      new EmailTelegramData("ingrese el correo electronico")


    };

    @Override
    public TelegramMessage sendText(String text, long id, UsersSessions usersSessions) {
        Match match = null;

        if( index >= limit) {
            // se termina la sesion del id y se envia la info al back

            PlayerCreationDTO p = new PlayerCreationDTO();

            p.setEmail( ((EmailTelegramData) buffer[email]).getcontents() );
            p.setPhoneNumber( ((PhoneTelegramData) buffer[phone]).getcontents() );

            // aqui se arma el objeto player y se envia el update al back
            ms.createPlayer(p, match.getId());
            System.out.println("jugador inscripto");
            usersSessions.deleteFinishSesion(this);
            this.responseMessage = "Jugador inscripto";
            return this;
        } else {
            if (index < limit && ( (TelegramData_I) buffer[index]).saveIfOK(text)) {


                if(!matchID){
                    try {
                        match = ms.getMatch(text.trim());
                        index += 1;
                        this.responseMessage = ((TelegramData_I) buffer[index]).getQuestion();
                        matchID =true;
                    } catch(Exception error) {
                        // no se pudo obtener el partido
                        this.responseMessage="id no valido, por favor intentelo de nuevo";
                        usersSessions.deleteFinishSesion(this);
                        return this;
                    }
                } else {
                    // logica para agregar el jugador al match recuperado
                    index += 1;

                    this.responseMessage = ((TelegramData_I) buffer[index]).getQuestion();
                }


            } else {
                // error en la carga de la info. se envia de respuesta el mensaje de error
                this.responseMessage = "Error en los datos cargador, por favor ingrese de nuevo";

            }
        }

     return this;
    }

    @Override
     public String responseMessage() {
        return this.responseMessage;
    }

    @Override
    public void setNextMessage() { this.responseMessage = ((TelegramData_I) buffer[index]).getQuestion();

    }

    @Override
    public void setMessage(String msg) {
        this.responseMessage = msg;
    }
}
