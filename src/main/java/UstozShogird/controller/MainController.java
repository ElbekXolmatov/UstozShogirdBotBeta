//package UstozShogird.controller;
//
//import UstozShogird.container.ComponentContainer;
//import UstozShogird.entity.Customer;
//import UstozShogird.service.CustomerService;
//import UstozShogird.util.KeyboardButtonUtil;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
//import org.telegram.telegrambots.meta.api.objects.*;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//public class MainController {
//    public static void handleMessage(User user, Message message) {
//
//        if (message.hasText()) {
//            String text = message.getText();
//            handleText(user, message, text);
//        } else if (message.hasContact()) {
//            Contact contact = message.getContact();
//            handleContact(user, message, contact);
//        }
//    }
//
//    private static void handleContact(User user, Message message, Contact contact) {
//
//        if(!contact.getPhoneNumber().matches("(\\+)?998\\d{9}")) return;
//
//        String chatId = String.valueOf(message.getChatId());
//        Customer customer = CustomerService.getCustomerByChatId(chatId);
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//
//        if (customer == null) {
//            customer = CustomerService.addCustomer(chatId, contact);
//
//        }else{
//            sendMessage.setText("Menu: ");
//            sendMessage.setReplyMarkup(KeyboardButtonUtil.getCustomerMenu());
//            ComponentContainer.MY_BOT.sendMsg(sendMessage);
//        }
//    }
//
//    private static void handleText(User user, Message message, String text) {
//        String chatId = String.valueOf(message.getChatId());
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//
//        if (text.equals("/start")) {
//            Customer customer = CustomerService.getCustomerByChatId(chatId);
//
//            if (customer == null) {
//                sendMessage.setText("Assalomu alaykum!");
//                sendMessage.setReplyMarkup(KeyboardButtonUtil.getContactMenu());
//                ComponentContainer.MY_BOT.sendMsg(sendMessage);
//            }else{
//                sendMessage.setText("Menu");
//                sendMessage.setReplyMarkup(KeyboardButtonUtil.getCustomerMenu());
//                ComponentContainer.MY_BOT.sendMsg(sendMessage);
//            }
//        }else{
//            Customer customer = CustomerService.getCustomerByChatId(chatId);
//            if(customer == null){
//                sendMessage.setText("Assalomu alaykum!");
//                sendMessage.setReplyMarkup(KeyboardButtonUtil.getContactMenu());
//                ComponentContainer.MY_BOT.sendMsg(sendMessage);
//            }else{
//                if(!customer.isActive()){
//                    if(text.equals(customer.getConfirmPassword())){
//                        customer.setActive(true);
//                        WorkWithFiles.writeCustomerList();
//
//                        sendMessage.setText("Menu");
//                        sendMessage.setReplyMarkup(KeyboardButtonUtil.getCustomerMenu());
//                        ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                    }else{
//                        sendMessage.setText("Kod noto'g'ri");
//                        ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                    }
//                }else{
//                    if(text.equals(KeyboardButtonConstants.ACCESS_VOTE)){
//                        if(ComponentContainer.startElection){
//                            if(!customer.isHasVoted()){
//                                for (Candidate candidate : Database.candidateList) {
//                                    new MyThread(candidate, customer).start();
//                                }
//                            }else{
//                                sendMessage.setText("Siz ovoz bergansiz.");
//                                ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                            }
//                        }else{
//                            sendMessage.setText("Ayni paytda aktiv saylov mavjud emas.");
//                            ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                        }
//                    }else if(text.equals(KeyboardButtonConstants.SHOW_COUNT_VOICE)){
//                        if(ComponentContainer.startElection){
//                            StringBuilder sb = new StringBuilder("Hozircha ovozlar soni: \n\n");
//
//                            Database.candidateList.sort((c1, c2) -> Integer.compare(c2.getCountVotes(), c1.getCountVotes()));
//
//                            for (Candidate candidate : Database.candidateList) {
//                                sb.append(candidate.getFullName()+" : "+candidate.getCountVotes()+"\n");
//                            }
//                            sendMessage.setText(sb.toString()+ LocalDateTime.now()+" paytiga ko'ra");
//
//                            ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                        }else{
//                            sendMessage.setText("Ayni paytda aktiv saylov mavjud emas.");
//                            ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                        }
//                    } else if(text.equals(KeyboardButtonConstants.CONNECT_TO_ADMIN)){
//
//                        ComponentContainer.customerMap.put(chatId, true);
//
//                        sendMessage.setText("Xabaringizni kiriting: ");
//                        ComponentContainer.MY_BOT.sendMsg(sendMessage);
//                    }else{
//                        if(ComponentContainer.customerMap.containsKey(chatId)){
//
//                            ComponentContainer.customerMap.remove(chatId);
//
//                            sendMessage.setText("Xabaringiz adminga jo'natildi.");
//                            ComponentContainer.MY_BOT.sendMsg(sendMessage);
//
//                            String str = "ChatId : "+customer.getChatId()+"\nFull name: "+customer.getFirstName()+
//                                    "\nPhone number: "+customer.getPhoneNumber()+
//                                    "\nText: "+text;
//                            SendMessage sendMessage1 = new SendMessage(ComponentContainer.ADMIN_CHAT_ID, str);
//                            sendMessage1.setReplyMarkup(InlineKeyboardUtil.getConnectMarkup(chatId));
//                            ComponentContainer.MY_BOT.sendMsg(sendMessage1);
//                        }
//                    }
//                }
//            }
//        }
//
//
//    }
//
//    public static void handleCallback(User user, Message message, String data) {
//        String chatId = String.valueOf(message.getChatId());
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//
//        if (data.startsWith("voting/")) {
//            String[] split = data.split("/");
//            String candidateId = split[1];
//
//            Candidate candidate = CandidateService.getCandidateById(candidateId);
//            Customer customer = CustomerService.getCustomerByChatId(chatId);
//
//            if (!customer.isHasVoted()) {
//                customer.setHasVoted(true);
//                candidate.setCountVotes(candidate.getCountVotes() + 1);
//
//                WorkWithFiles.writeCustomerList();
//                WorkWithFiles.writeCandidateList();
//
//                sendMessage.setText("siz " + candidate.getFullName() + " ga ovoz berdingiz.");
//
//                for (Message message1 : Database.messageList) {
//                    if(String.valueOf(message1.getChatId()).equals(chatId)){
//                        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message1.getChatId()), message1.getMessageId());
//                        ComponentContainer.MY_BOT.sendMsg(deleteMessage);
//                    }
//                }
//
//                Database.messageList.removeIf(message1 -> message1.getChatId().toString().equals(chatId));
//
//            } else {
//                sendMessage.setText("siz avval ovoz bergansiz");
//            }
//
//            ComponentContainer.MY_BOT.sendMsg(sendMessage);
//        }
//
//
//    }
//
//}
//
