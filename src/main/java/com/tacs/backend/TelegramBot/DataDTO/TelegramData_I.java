package com.tacs.backend.TelegramBot.DataDTO;

import java.util.Date;

public interface TelegramData_I {


    public Boolean saveIfOK(String string);

    public String getQuestion();

    public void setQuestion(String question);




}
