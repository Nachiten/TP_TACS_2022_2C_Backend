package com.tacs.backend.TelegramBot;

public interface TelegramMessage {


    public long getUserID();

    public void setUserID(long userID);

    public TelegramMessage sendText(String text, long id,UsersSessions usersSessions);

    public String responseMessage();

    public void setNextMessage();

    public void setMessage(String msg);

}
