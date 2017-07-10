package org.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MyAmtBot extends TelegramLongPollingBot
{

    static Logger log = LoggerFactory.getLogger(MyAmtBot.class.getName());

    public void onUpdateReceived(Update update)
    {

        if (update.hasMessage() && update.getMessage().hasText())
        {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            //send("Opening AMT", chat_id);

            message_text = Amt.getDetails(message_text);

            if (message_text.equals("not"))
            {
                send("Some error occured, try again", chat_id);
                return;
            } else if (message_text.equals(""))
            {
                send("Please send only number", chat_id);
                return;
            }

            //log.info("Sending : " + message_text);
            send(message_text, chat_id);

        }
    }


    public String getBotUsername()
    {
        return "ArpitAmtBot";
    }

    @Override
    public String getBotToken()
    {
        // Return bot token from BotFather
        return "407376581:AAHn8WqQ2MEalHl-DNAGdloslbvXL866kmw";
    }

    public void send(String message_text, long chat_id)
    {
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(message_text);
        try
        {
            sendMessage(message);
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
}