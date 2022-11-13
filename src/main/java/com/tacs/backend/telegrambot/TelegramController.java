package com.tacs.backend.telegrambot;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.tacs.backend.dto.creation.MatchCreationDTO;
import com.tacs.backend.dto.creation.PlayerCreationDTO;
import com.tacs.backend.exception.EntityNotFoundException;
import com.tacs.backend.model.Match;
import com.tacs.backend.model.Player;
import com.tacs.backend.service.MatchService;
import com.tacs.backend.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@BotController
public class TelegramController  implements TelegramMvcController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private StatisticsService statisticsService;

    @Value("${bot.token}")
    private String token;

    @Override
    public String getToken() {
        return token;
    }

    @BotRequest(value = "**")
    public SendMessage anyText(Chat chat, User user) {
        return generateMessageResponse("Welcome to TACS Bot "  + user.firstName() + "! Use /help for the command list", chat);
    }

    @BotRequest(value = "/**")
    public SendMessage invalidCommand(Chat chat) {
        return generateMessageResponse("Invalid command. Use /help for the command list", chat);
    }

    @BotRequest(value = "/help")
    public SendMessage getHelp(Chat chat) {
        return generateMessageResponse("""
            *Possible commands:*
            `/newMatch <startingDateTime>(yyyy-MM-dd HH:mm) <location>(For spaces use _)`
            Create new match
            
            `/getMatch <matchId>`
            Get match info
            
            `/newPlayer <matchId> <phoneNumber> <email>`
            Create new player
          
            `/statistics <players|matches> <hours>`
            Get statistics for the last hours
            
            `/help`
            Get this help
            """
            , chat);
    }

    @BotRequest(value = "/getMatch{( [\\S]+){0,1}}{$}", type = {MessageType.ANY})
    public SendMessage getMatchByIdWrongArgs(Chat chat) {
        return generateMessageResponse("Invalid params. Usage:\n`/getMatch <matchId>`", chat);
    }

    @BotRequest(value = "/getMatch {id:[\\S]+}")
    public SendMessage getMatchById(@BotPathVariable("id") String matchId, Chat chat) {
        try {
            Match match = matchService.getMatch(matchId);

            return generateMessageResponse("*Match found:* \n" + match.toString(), chat);
        } catch (EntityNotFoundException e) {
            return generateMessageResponse("*Error:* No match found.", chat);
        }
    }

    @BotRequest(value = "/statistics{( [\\S]+){0,1}}{$}", type = {MessageType.ANY})
    public SendMessage getStatisticsWrongArgs(Chat chat) {
        return generateMessageResponse("Invalid params. Usage:\n`/statistics <players|matches> <hours>`", chat);
    }

    @BotRequest(value = "/statistics {statType:[\\S]+} {hours:[\\S]+}")
    public SendMessage getStatistics(@BotPathVariable("statType") String statType, @BotPathVariable("hours") String hours, Chat chat) {

        int hoursInt = 0;

        try {
            hoursInt = Integer.parseInt(hours);
        } catch (NumberFormatException e) {
            return generateMessageResponse("*Error:* Invalid 'hours' value", chat);
        }

        String returnString;

        switch (statType) {
            case "players" -> returnString = "Players created in the last " + hours + " hours: " + statisticsService.getPlayersCreatedInLastHours(hoursInt).getPlayersEnrolled();
            case "matches" -> returnString = "Matches created in the last " + hours + " hours: " + statisticsService.getMatchesCreatedInLastHours(hoursInt).getMatchesCreated();
            default -> returnString = "Invalid first argument. Only possible values are: players, matches";
        };

        return generateMessageResponse(returnString, chat);
    }

    @BotRequest(value = "/newMatch{( [\\S]+){0,2}}{$}", type = {MessageType.ANY})
    public SendMessage newMatchWrongArgs(Chat chat) {
        return generateMessageResponse( "Invalid params. Usage:\n`/newMatch <startingDateTime>(yyyy-MM-dd HH:mm) <location>(For spaces use _)`", chat);
    }

    @BotRequest(value = "/newMatch {startingDate:[\\S]+} {startingTime:[\\S]+} {location:[\\S]+}", type = {MessageType.MESSAGE})
    public SendMessage newMatch(@BotPathVariable("startingDate") String startingDate, @BotPathVariable("startingTime") String startingTime , @BotPathVariable("location") String location, Chat chat) {

        // Replace _ in location with spaces
        String locationWithSpaces = location.replace("_", " ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime dateTime;

        String dateTimeString = startingDate + " " +  startingTime;

        try {
            dateTime = LocalDateTime.parse(dateTimeString, formatter);
        } catch (Exception e) {
            return generateMessageResponse("*Error:* Invalid 'startingDateTime' format. Use yyyy-MM-dd HH:mm", chat);
        }

        MatchCreationDTO matchCreationDTO = new MatchCreationDTO(dateTime, locationWithSpaces);

        try {
            Match match = matchService.createMatch(matchCreationDTO);

            return generateMessageResponse("Match was created successfully. Id: " + match.getId(), chat);
        } catch (Exception e) {
            return generateMessageResponse("*Error:* " + e.getMessage(), chat);
        }
    }

    @BotRequest(value = "/newPlayer{( [\\S]+){0,2}}{$}", type = {MessageType.ANY})
    public SendMessage newPlayerWrongArgs(Chat chat) {
        return generateMessageResponse("Invalid params. Usage:\n`/newPlayer <matchId> <phoneNumber> <email>`", chat);
    }

    @BotRequest(value = "/newPlayer {matchId:[\\S]+} {phoneNumber:[\\S]+} {email:[\\S]+}")
    public SendMessage newPlayer(@BotPathVariable("matchId") String matchId, @BotPathVariable("phoneNumber") String phoneNumber , @BotPathVariable("email") String email, Chat chat) {

        long phoneNumberParsed;

        // Parse phone number as long
        try {
            phoneNumberParsed = Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            return generateMessageResponse("*Error:* Invalid 'phoneNumber'. Must be a number", chat);
        }

        // Check email against regex
        if (!email.matches(".+@.+[.].+")) {
            return generateMessageResponse("*Error:* Invalid 'email'", chat);
        }

        try {
            Player createdPlayer = matchService.createPlayer(new PlayerCreationDTO(phoneNumberParsed, email), matchId);
            return generateMessageResponse("Player was enrolled successfully. Is Regular: " + createdPlayer.getIsRegular(), chat);
        } catch (Exception e) {
            return generateMessageResponse("*Error:* " + e.getMessage(), chat);
        }
    }

    private SendMessage generateMessageResponse(String message, Chat chat) {
        SendMessage sendMessage = new SendMessage(chat.id(), message);
        sendMessage.parseMode(ParseMode.Markdown);

        return sendMessage;
    }
}
