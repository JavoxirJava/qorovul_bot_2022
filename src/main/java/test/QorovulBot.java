package test;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class QorovulBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                String text = update.getMessage().getText();
                if (isRek(text)) {
                    delMsg(update);
                }
            }
        }
    }

    public void delMsg(Update update) {
        DeleteMessage del = new DeleteMessage();
        del.setMessageId(update.getMessage().getMessageId());
        del.setChatId(update.getMessage().getChatId().toString());
        try {
            execute(del);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("bajarilmadi");
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMSG(sendMessage, "iltimos reklama tarqatmang " + update.getMessage().getFrom().getFirstName()
                + "\nbot @M_Javoxir_1 tomonidan yozildi:");
    }

    public void sendMSG(SendMessage sendMessage, String text) {
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("junatilmadi:");
        }
    }

    public boolean isRek(String text) {
        return  text.contains("https") || text.contains("t.me/") || text.contains("@") || text.contains(".uz") ||
                text.contains(".ru") || text.contains(".com") || text.contains("http");
    }

    @Override
    public String getBotUsername() {
        return Template.BOT_USER;
    }

    @Override
    public String getBotToken() {
        return Template.BOT_TOKEN;
    }
}
