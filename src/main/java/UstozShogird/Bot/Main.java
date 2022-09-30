package UstozShogird.Bot;

import UstozShogird.container.ComponentContainer;
import UstozShogird.files.WorkWithFiles;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {

        try {

            WorkWithFiles.readCustomerList();
            WorkWithFiles.readCandidateList();

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            UstozShogirdBot myBot = new UstozShogirdBot();
            ComponentContainer.MY_BOT = myBot;

            botsApi.registerBot(myBot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
