package UstozShogird.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class KeyboardButtonUtil {

    public static ReplyKeyboard getAdminMenu() {
        List<KeyboardRow> rowList = getRowList(
                getRow(
                        getButton(KeyboardButtonConstants.ADD_ANNOUNCEMENT_COLLEAGUE),
                        getButton(KeyboardButtonConstants.ADD_ANNOUNCEMENT_NEED_WORK)),
                getRow(
                        getButton(KeyboardButtonConstants.ADD_ANNOUNCEMENT_NEED_WORK),
                        getButton(KeyboardButtonConstants.ADD_ANNOUNCEMENT_TEACHER)
                ),
                getRow(
                        getButton(KeyboardButtonConstants.ADD_ANNOUNCEMENT_STUDENT)
                ));

        return getMarkup(rowList);
    }

    private static KeyboardButton getButton(String demo) {
        return new KeyboardButton(demo);
    }

    private static KeyboardRow getRow(KeyboardButton... buttons) {
        return new KeyboardRow(List.of(buttons));
    }

    private static List<KeyboardRow> getRowList(KeyboardRow... rows) {
        return List.of(rows);
    }

    private static ReplyKeyboardMarkup getMarkup(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rowList);
        markup.setResizeKeyboard(true);
        markup.setSelective(true);
        return markup;
    }
}
