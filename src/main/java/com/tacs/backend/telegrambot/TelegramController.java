package com.tacs.backend.telegrambot;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.model.Chat;
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
    public String anyText() {
        return "Welcome to TACS Bot. /help for command list";
    }

    @BotRequest(value = "/**")
    public String invalidCommand() {
        return "Invalid command. /help for command list";
    }

    @BotRequest(value = "/help")
    public String getHelp() {
        return "Possible commands:\n/newMatch <startingDateTime>(yyyy-MM-dd HH:mm) <location>: Create new match\n/getMatch <matchId>: Get match info\n/newPlayer <matchId> <phoneNumber> <email>: Create new player\n/statistics <players|matches> <hours>: Get statistics for the last hours\n/help: Get this help";
    }

    @BotRequest(value = "/getMatch{( [\\S]+){0,1}}{$}", type = {MessageType.ANY})
    public String getMatchByIdWrongArgs() {
        return "Invalid params. Usage:\n/getMatch <matchId>";
    }

    @BotRequest(value = "/getMatch {id:[\\S]+}")
    public SendMessage getMatchById(@BotPathVariable("id") String matchId, Chat chat) {
        try {
            Match match = matchService.getMatch(matchId);

            return generateMessageResponse("*Match found:* \n" + match.toString(), chat);
        } catch (EntityNotFoundException e) {
            return generateMessageResponse("No match found.", chat);
        }
    }

    private final String invalidParamsStatistics = "Invalid params. Usage:\n/statistics <players|matches> <hours>";

    @BotRequest(value = "/statistics{( [\\S]+){0,1}}{$}", type = {MessageType.ANY})
    public String getStatisticsWrongArgs() {
        return "Invalid params. Usage:\n/statistics <players|matches> <hours>";
    }

    @BotRequest(value = "/statistics {statType:[\\S]+} {hours:[\\S]+}")
    public String getStatistics(@BotPathVariable("statType") String statType, @BotPathVariable("hours") String hours) {

        int hoursInt = 0;

        try {
            hoursInt = Integer.parseInt(hours);
        } catch (NumberFormatException e) {
            return "Invalid hours value";
        }

        return switch (statType) {
            case "players" -> "Players created in the last " + hours + " hours: " + statisticsService.getPlayersCreatedInLastHours(hoursInt).getPlayersEnrolled();
            case "matches" -> "Matches created in the last " + hours + " hours: " + statisticsService.getMatchesCreatedInLastHours(hoursInt).getMatchesCreated();
            default -> invalidParamsStatistics;
        };
    }

    @BotRequest(value = "/newMatch{( [\\S]+){0,2}}{$}", type = {MessageType.ANY})
    public String newMatchWrongArgs() {
        return "Invalid params. Usage:\n/newMatch <startingDateTime>(yyyy-MM-dd HH:mm) <location>";
    }

    @BotRequest(value = "/newMatch {startingDate:[\\S]+} {startingTime:[\\S]+} {location:[\\S]+}", type = {MessageType.MESSAGE})
    public SendMessage newMatch(@BotPathVariable("startingDate") String startingDate, @BotPathVariable("startingTime") String startingTime , @BotPathVariable("location") String location, Chat chat) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime dateTime;

        String dateTimeString = startingDate + " " +  startingTime;

        try {
            dateTime = LocalDateTime.parse(dateTimeString, formatter);
        } catch (Exception e) {
            return generateMessageResponse("Invalid date format. Use yyyy-MM-dd HH:mm", chat);
        }

        MatchCreationDTO matchCreationDTO = new MatchCreationDTO(dateTime, location);

        try {
            Match match = matchService.createMatch(matchCreationDTO);

            return generateMessageResponse("Match was created successfully. Id: " + match.getId(), chat);
        } catch (Exception e) {
            return generateMessageResponse("Error creating match: " + e.getMessage(), chat);
        }
    }

    @BotRequest(value = "/newPlayer{( [\\S]+){0,2}}{$}", type = {MessageType.ANY})
    public String newPlayerWrongArgs() {
        return "Invalid params. Usage:\n/newPlayer <matchId> <phoneNumber> <email>";
    }

    @BotRequest(value = "/newPlayer {matchId:[\\S]+} {phoneNumber:[\\S]+} {email:[\\S]+}")
    public SendMessage newPlayer(@BotPathVariable("matchId") String matchId, @BotPathVariable("phoneNumber") String phoneNumber , @BotPathVariable("email") String email, Chat chat) {

        long phoneNumberParsed;

        try {
            phoneNumberParsed = Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            return generateMessageResponse("Invalid phone number. Must be a number", chat);
        }

        try {
            Player createdPlayer = matchService.createPlayer(new PlayerCreationDTO(phoneNumberParsed, email), matchId);
            return generateMessageResponse("Player was enrolled successfully. Is Regular: " + createdPlayer.getIsRegular(), chat);
        } catch (Exception e) {
            return generateMessageResponse("Error enrolling player: " + e.getMessage(), chat);
        }
    }

    private SendMessage generateMessageResponse(String message, Chat chat) {
        SendMessage sendMessage = new SendMessage(chat.id(), message);
        sendMessage.parseMode(ParseMode.Markdown);

        return sendMessage;
    }
}
