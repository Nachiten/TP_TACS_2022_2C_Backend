package com.tacs.backend.TelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBoot extends TelegramLongPollingBot {

    UsersSessions usersSesionActive = new UsersSessions();

    @Override
    public void onUpdateReceived(Update update) {

        String dataReceive = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        // se crea un Objeto para enviar las respuesta
        SendMessage message = new SendMessage();

        // System.out.println(Math.toIntExact(usersSesionActive.quantityIdUsers(chatId)));
        //Math.toIntExact(usersSesionActive.quantityIdUsers(chatId))
           if(!usersSesionActive.existsIdUser(chatId)) {
               System.out.println("ingresa un nuevo cliente");
               String response = usersSesionActive.newSesion(chatId, dataReceive);
               message.setChatId(chatId);
               message.setText(response);
               System.out.println(response);


           } else {
                // ya hay una conversacion en curso
               String respond = usersSesionActive.deliverMessage(dataReceive, chatId);
               System.out.println("entro en la segunda opcion");
               message.setChatId(chatId);
               message.setText(respond);
           }




        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }






    }
   // name of the bot: tacs_footbal_bot
   // bot's username : tacs_bot
    // key for bot: 5695697310:AAGJE7KaEj2-VJXhkUD-E2sFS3rMxSAcJBE
    // bot description: https://core.telegram.org/bots/api
    // bot URL: t.me/tacs_bot
    // help of the bot: t.me/tacs_bot/help


    @Override
    public String getBotUsername() {
        return "tacs_bot";
    }

    @Override
    public String getBotToken() {
        return "5695697310:AAGJE7KaEj2-VJXhkUD-E2sFS3rMxSAcJBE";
    }
}
