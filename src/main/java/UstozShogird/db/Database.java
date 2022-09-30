package UstozShogird.db;

import UstozShogird.entity.Announce;
import UstozShogird.entity.Customer;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public interface Database {
    List<Customer> customerList = new ArrayList<>();
    List<Announce>  announceList = new ArrayList<>();
    List<Message> messageList = new ArrayList<>();
}
