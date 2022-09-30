package UstozShogird.container;

import UstozShogird.Bot.UstozShogirdBot;
import UstozShogird.entity.Announce;
import UstozShogird.enums.AdminStatus;


import java.util.HashMap;
import java.util.Map;

public class ComponentContainer {
    public static UstozShogirdBot MY_BOT = null;
    public static String BOT_USERNAME = "SheengoLearningBot";
    public static String BOT_TOKEN = "5679397184:AAEm59wozDs5hm31pF7Lu7jV--pe1eFyV_M";
    public static String ADMIN_CHAT_ID = "5246644409";

    public static boolean startElection = false;

    // adminChatId, AdminStatus
    public static Map<String, AdminStatus> statusMap = new HashMap<>();
    public static Map<String, Announce> announceMap = new HashMap<>();
}
