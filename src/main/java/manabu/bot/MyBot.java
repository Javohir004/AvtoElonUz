package manabu.bot;
import manabu.Repository.PhotoRepository;
import manabu.bot.handlers.CommandHandler;
import manabu.enomerator.UserState;
import manabu.model.User;
import manabu.service.CarService;
import manabu.service.PhotoService;
import manabu.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


public class MyBot extends TelegramLongPollingBot {

    public MyBot() {
        super(""); /// bu yerga token berasiz
    }

    public static Buttons buttons = new Buttons();

  public  static CarService carService = CarService.getInstance();
    public static UserService userService = UserService.getInstance();

    public static PhotoService photoService = new PhotoService(new PhotoRepository());

    public static CommandHandler commandHandler = new CommandHandler();


    @Override
    public void onUpdateReceived(Update update) {

    if(update.getMessage().hasPhoto()){
        Long chatId = update.getMessage().getChatId();
        User user = userService.findByChatId(chatId);
        if(!user.getState().equals(UserState.RECEIVING_CARPHOT0)) {
            SendMessage sendMessage = new SendMessage(chatId.toString(), "You can't send media or photo until We ask from you !!!");
            send(sendMessage);
            return;
        }
    }


      SendMessage sendMessage = commandHandler.handle(update.getMessage());
      send(sendMessage);
    }


    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPhotoFromMyBot(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendVoice(SendVoice sendVoice){
        try {
            execute(sendVoice);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "Build_your_dreambot";
    }


}
