package com.tacs.backend.TelegramBot;

public class TelegramCreateMatch implements TelegramMessage {

    long userID = 0;
    String responseMessage = "ingrese la fecha:";
    int  index = 0;
    final int respondToUser = 0;
    final int respondFromUser = 1;
    final int limit =3;
    String[][] data = {
            {"ingrese la fecha:",""},
            {"ingrese la hora",""},
            {"ingrese el lugar",""},
            {"partido generado",""}

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
    public TelegramMessage sendText(String text,long userID,UsersSessions usersSessions) {
        // se procesa los datos recibidos y se chequean si estan correctos
        // falta corroborar si es correcta la respuesta


        if(index < limit) {
            data[index][respondFromUser] = text;
            index += 1;
            this.responseMessage = data[index][respondToUser].toString();
            //System.out.println("el index es " + index + "y el respond M es: " + this.responseMessage);
            if( index == limit) {
                // se termina la sesion del id
                usersSessions.deleteFinishSesion(this);

                //System.out.println(data[0][1].toString());
                //System.out.println(data[1][1].toString());
                //System.out.println(data[2][1].toString());
            }
        }

        return this;
    }

    @Override
    public String responseMessage() {


        return this.responseMessage;
    }


    public void setNextMessage(){
        this.responseMessage = data[index][respondFromUser].toString();
    }

    @Override
    public void setMessage(String msg) {

    }


}
