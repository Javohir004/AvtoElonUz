package manabu.bot.handlers;

import manabu.enomerator.UserState;
import manabu.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static manabu.bot.MyBot.buttons;
import static manabu.bot.MyBot.userService;


public class RegistrationHandler {

    public SendMessage handleContact(Message message, User currentUser) {
        Long chatId = message.getChatId();
        if (message.hasContact()) {
            Contact contact = message.getContact();
            if (!Objects.equals(contact.getUserId(), chatId)) {
                return new SendMessage(chatId.toString(), "Please share your own number");
            }
//            currentUser.setState(UserState.SHARE_LOCATION);
//            userService.update(currentUser);
//            SendMessage sendMessage = new SendMessage(chatId.toString(), "thank you, please share your location");
//            sendMessage.setReplyMarkup(buttons.requestLocation());
            currentUser.setPhoneNumber(contact.getPhoneNumber());
            currentUser.setState(UserState.REGISTERED);
            userService.update(currentUser);
            SendMessage sendMessage = new SendMessage(chatId.toString(), "thank you");
            sendMessage.setReplyMarkup(buttons.getUserRole());

            return sendMessage;
        }
        return new SendMessage(chatId.toString(), "please send your contact");
    }


    public SendMessage handleLocation(Message message, User currentUser) {
        Long chatId = message.getChatId();
        if (message.hasLocation()) {
            Location location = message.getLocation();
            currentUser.setLocation(location);
            currentUser.setState(UserState.REGISTERED);
            userService.update(currentUser);
            SendMessage sendMessage = new SendMessage(chatId.toString(), "thank you");
            sendMessage.setReplyMarkup(buttons.getUserRole());
            return sendMessage;
        }
        return new SendMessage(chatId.toString(), "please send your location");
    }


}
