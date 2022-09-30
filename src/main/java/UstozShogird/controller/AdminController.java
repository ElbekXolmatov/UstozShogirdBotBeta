package UstozShogird.controller;

import UstozShogird.container.ComponentContainer;
import UstozShogird.db.Database;
import UstozShogird.entity.Announce;
import UstozShogird.entity.Customer;
import UstozShogird.enums.AdminStatus;
import UstozShogird.files.WorkWithFiles;
import UstozShogird.service.CandidateService;
import UstozShogird.util.InlineButtonConstants;
import UstozShogird.util.InlineKeyboardUtil;
import UstozShogird.util.KeyboardButtonConstants;
import UstozShogird.util.KeyboardButtonUtil;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;


public class AdminController {
    public static void handleMessage(User user, Message message) {

        if (message.hasText()) {
            String text = message.getText();
            handleText(user, message, text);
        }
    }


    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.equals("/start")) {
            sendMessage.setText("Hello Dear "  +user.getUserName() + " Know you will get some questions");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.getAdminMenu());
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        } else if (text.equals(KeyboardButtonConstants.ADD_ANNOUNCEMENT_COLLEAGUE)) {

            ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_FULL_NAME);
            ComponentContainer.announceMap.put(chatId, new Announce());

            sendMessage.setText("Ism, familiyangizni kiriting?:");
            ComponentContainer.MY_BOT.sendMsg(sendMessage);


        }else {
            if (ComponentContainer.statusMap.containsKey(chatId)) {
                AdminStatus adminStatus = ComponentContainer.statusMap.get(chatId);
                if (adminStatus.equals(AdminStatus.ENTER_FULL_NAME)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setFullName(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_TECHNOLOGY);

                    sendMessage.setText("\uD83D\uDCDA Texnologiya:\n" +
                            "\n" +
                            "Talab qilinadigan texnologiyalarni kiriting?\n" +
                            "Texnologiya nomlarini vergul bilan ajrating. Masalan, \n" +
                            "\n" +
                            "Java, C++, C#");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }
                else if (adminStatus.equals(AdminStatus.ENTER_TECHNOLOGY)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setTechnology(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_NUMBER);

                    sendMessage.setText("\uD83D\uDCDE Aloqa: \n" +
                            "\n" +
                            "Bog`lanish uchun raqamingizni kiriting?\n" +
                            "Masalan, +998 90 123 45 67");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }
                else if (adminStatus.equals(AdminStatus.ENTER_NUMBER)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setPhoneNumber(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_PLACE);

                    sendMessage.setText("\uD83C\uDF10 Hudud: \n" +
                            "\n" +
                            "Qaysi hududdansiz?\n" +
                            "Viloyat nomi, Toshkent shahar yoki Respublikani kiriting.");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }else if (adminStatus.equals(AdminStatus.ENTER_PLACE)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setPlace(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_PRICE);

                    sendMessage.setText("\uD83D\uDCB0 Narxi:\n" +
                            "\n" +
                            "Tolov qilasizmi yoki Tekinmi?\n" +
                            "Kerak bo`lsa, Summani kiriting?");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }else if (adminStatus.equals(AdminStatus.ENTER_PRICE)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setPrice(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_JOB);

                    sendMessage.setText("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB Kasbi: \n" +
                            "\n" +
                            "Ishlaysizmi yoki o`qiysizmi?\n" +
                            "Masalan, Talaba");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }else if (adminStatus.equals(AdminStatus.ENTER_JOB)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setJob(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_TIME_TO_CALL);

                    sendMessage.setText("\uD83D\uDD70 Murojaat qilish vaqti: \n" +
                            "\n" +
                            "Qaysi vaqtda murojaat qilish mumkin?\n" +
                            "Masalan, 9:00 - 18:00");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }else if (adminStatus.equals(AdminStatus.ENTER_TIME_TO_CALL)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setTimeToCall(text);

                    ComponentContainer.statusMap.put(chatId, AdminStatus.ENTER_PURPOSE);

                    sendMessage.setText("\uD83D\uDD0E Maqsad: \n" +
                            "\n" +
                            "Maqsadingizni qisqacha yozib bering.");
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }else if (adminStatus.equals(AdminStatus.ENTER_PURPOSE)) {
                    Announce announce = ComponentContainer.announceMap.get(chatId);
                    announce.setPurpose(text);
                    announce.setUserName(message.getChat().getUserName());


                    ComponentContainer.statusMap.put(chatId, AdminStatus.CONFIRM_OR_CANCEL);

                    StringBuilder sb = new StringBuilder();

                    sb.append("\uD83C\uDFC5 Sherik: "+ announce.getFullName()+ "\n");
                    sb.append("\uD83D\uDCDA Texnologiya:"+ announce.getTechnology()+ "\n");
                    sb.append("\uD83C\uDDFA\uD83C\uDDFF Telegram: "+announce.getUserName()+ "\n");
                    sb.append("\uD83D\uDCDE Aloqa: "+ announce.getPhoneNumber()+ "\n");
                    sb.append("\uD83C\uDF10 Hudud: "+ announce.getPlace()+ "\n");
                    sb.append("\uD83D\uDCB0 Narxi: "+ announce.getPrice()+ "\n");
                    sb.append("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB Kasbi: "+ announce.getJob()+ "\n");
                    sb.append("\uD83D\uDD70 Murojaat qilish vaqti: "+ announce.getTimeToCall()+ "\n");
                    sb.append("\uD83D\uDD0E Maqsad: "+ announce.getPurpose()+ "\n");


                    SendMessage message1 = new SendMessage(chatId,sb.toString());
                    message1.setReplyMarkup(InlineKeyboardUtil.getConfirmOrCancelMenu());

                    ComponentContainer.MY_BOT.sendMsg(message1);
                }
            }
        }

    }

    private static void sendMessageToCustomersForElection() {

        for (Customer customer : Database.customerList) {
            for (Announce announce : Database.announceList) {
                new MyThread(announce, customer).start();
            }
        }

    }

    private static void sendMessageToCustomers(String message) {
        for (Customer customer : Database.customerList) {
            SendMessage sendMessage = new SendMessage(customer.getChatId(), message);
            ComponentContainer.MY_BOT.sendMsg(sendMessage);
        }
    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (data.equals(InlineButtonConstants.CONFIRM_CALL_BACK)) {
            Announce announce = ComponentContainer.announceMap.get(chatId);
            CandidateService.addCandidate(announce);

            sendMessage.setText(announce.getFullName() + " muvaffaqiyatli saqlandi.");
        } else if (data.equals(InlineButtonConstants.CANCEL_CALL_BACK)) {
            Announce announce = ComponentContainer.announceMap.get(chatId);
            sendMessage.setText(announce.getFullName() + " saqlanmadi.");
        }

        ComponentContainer.statusMap.remove(chatId);
        ComponentContainer.announceMap.remove(chatId);

        ComponentContainer.MY_BOT.sendMsg(sendMessage);

        DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
        ComponentContainer.MY_BOT.sendMsg(deleteMessage);
    }
}

@AllArgsConstructor
class MyThread extends Thread{
    private Announce announce;
    private Customer customer;


    @Override
    public void run() {

//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setChatId(customer.getChatId());
//        sendPhoto.setPhoto(new InputFile(announce.getFileId()));
//        sendPhoto.setCaption(announce.getFullName()+"\n"+announce.getDescription());
//
//        sendPhoto.setReplyMarkup(InlineKeyboardUtil.getVotingMenu(announce.getId(), announce.getCountVotes()));
//        ComponentContainer.MY_BOT.sendMsg(sendPhoto);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(customer.getChatId());
        StringBuilder sb = new StringBuilder();

        sb.append("\uD83C\uDFC5 Sherik: "+ announce.getFullName()+ "\n");
        sb.append("\uD83D\uDCDA Texnologiya:"+ announce.getTechnology()+ "\n");
        sb.append("\uD83C\uDDFA\uD83C\uDDFF Telegram: "+ announce.getUserName()+ "\n");
        sb.append("\uD83D\uDCDE Aloqa: "+ announce.getPhoneNumber()+ "\n");
        sb.append("\uD83C\uDF10 Hudud: "+ announce.getPlace()+ "\n");
        sb.append("\uD83D\uDCB0 Narxi: "+ announce.getPrice()+ "\n");
        sb.append("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB Kasbi: "+ announce.getJob()+ "\n");
        sb.append("\uD83D\uDD70 Murojaat qilish vaqti: "+ announce.getTimeToCall()+ "\n");
        sb.append("\uD83D\uDD0E Maqsad: "+ announce.getPurpose()+ "\n");
        sendMessage.setText(sb.toString());
        ComponentContainer.MY_BOT.sendMsg(sendMessage);
    }
}