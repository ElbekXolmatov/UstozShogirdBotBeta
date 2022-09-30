package UstozShogird.controller;

import UstozShogird.container.ComponentContainer;
import UstozShogird.entity.Customer;
import UstozShogird.service.CustomerService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

public class MainController {
    public static void handleMessage(User user, Message message) {

        if (message.hasText()) {
            String text = message.getText();
            handleText(user, message, text);
        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            handleContact(user, message, contact);
        }
    }

    private static void handleContact(User user, Message message, Contact contact) {
        String chatId = String.valueOf(message.getChatId());
    }

    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Customer customer = CustomerService.getCustomerByChatId(chatId);

        if(customer == null){
            CustomerService.addCustomer(chatId,
                    new Contact("+998901234567", "f", "l", 1234567l, "vcard"));
        }

        if (text.equals("/start")) {
            sendMessage.setText("Assalomu alaykum!");
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        }

    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        ComponentContainer.MY_BOT.sendMsg(sendMessage);


    }

}

