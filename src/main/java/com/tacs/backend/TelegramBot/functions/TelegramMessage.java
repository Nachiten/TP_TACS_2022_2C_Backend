package com.tacs.backend.TelegramBot.functions;

import com.tacs.backend.TelegramBot.CORE.UsersSessions;

public interface TelegramMessage {


    public long getUserID();

    public void setUserID(long userID);

    public TelegramMessage sendText(String text, long id, UsersSessions usersSessions);

    public String responseMessage();

    public void setNextMessage();

    public void setMessage(String msg);

}
