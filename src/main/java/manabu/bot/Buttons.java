package manabu.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {


    public  ReplyKeyboardMarkup getUserRole(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("SELLER\uD83E\uDD11");
        row.add("BUYER\uD83E\uDD20");
        keyboardRows.add(row);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup justBack(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Back➡\uFE0F");
        keyboardRows.add(row);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getCarPictureResponce(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("All picture sent \uD83D\uDDBC!!!");
        row1.add("Back➡\uFE0F");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getAsnwereExam(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Send excel file");
        row1.add("Send to email");
        keyboardRows.add(row1);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getSellMenu(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Show all\uD83D\uDC40");
        row1.add("CRUD car\uD83D\uDC4A");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Back➡\uFE0F");

        keyboardRows.add(row1);
        keyboardRows.add(row3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getCRUDButtons(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Show\uD83D\uDC40");
        row1.add("Create\uD83D\uDC4A");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Update♻!!!");
        row2.add("Delete\uD83D\uDDD1");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Back➡\uFE0F");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup updateButtons(){
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Full update \uD83D\uDD0D");
        row1.add("I sold car \uD83E\uDD1D!!!");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Back➡\uFE0F");

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup requestContact() {
        KeyboardButton button = new KeyboardButton("\uD83D\uDCDEShare your number");
        button.setRequestContact(true);
        ReplyKeyboardMarkup replyKeyboardMarkup =
                new ReplyKeyboardMarkup(List.of(new KeyboardRow(List.of(button))));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup requestLocation() {
        KeyboardButton button = new KeyboardButton("\uD83D\uDCCDShare your location");
        button.setRequestLocation(true);
        ReplyKeyboardMarkup replyKeyboardMarkup =
                new ReplyKeyboardMarkup(List.of(new KeyboardRow(List.of(button))));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineExample() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("USD");
        button.setCallbackData("USD");
        row.add(button);

        button = new InlineKeyboardButton("EUR");
        button.setCallbackData("EUR");
        row.add(button);

        rows.add(row);

        row = new ArrayList<>();

        button = new InlineKeyboardButton("UZS");
        button.setCallbackData("UZS");
        row.add(button);

        button = new InlineKeyboardButton("RUB");
        button.setCallbackData("RUB");
        row.add(button);

        rows.add(row);

        return new InlineKeyboardMarkup(rows);
    }
}
