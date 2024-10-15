package manabu.bot.handlers;
import lombok.extern.slf4j.Slf4j;
import manabu.bot.MyBot;
import manabu.enomerator.CarStatus;
import manabu.enomerator.UserRole;
import manabu.enomerator.UserState;
import manabu.exception.DataNotFoundException;
import manabu.model.Car;
import manabu.model.Photo;
import manabu.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

@Slf4j
public class CommandHandler extends MyBot{

    RegistrationHandler registrationHandler = new RegistrationHandler();

    static Car currentCar;

    public  SendMessage handle(Message message) {
        String text = message.getText();
        Long chatId = message.getChatId();

        if(Objects.equals(text, "/start")) {
            SendVoice sendVoice = new SendVoice(chatId.toString(),
                    new InputFile(new File("C:\\Users\\user\\Downloads\\audio_2024-05-29_12-00-28.ogg")));
            sendVoice(sendVoice);

            return handleStart(chatId, message.getFrom());
        }

        User currentUser = userService.findByChatId(chatId);
        switch (currentUser.getState()) {
            case SHARE_CONTACT -> {
                return registrationHandler.handleContact(message, currentUser);
            }
            case SHARE_LOCATION -> {
                return registrationHandler.handleLocation(message, currentUser);
            }
            case REGISTERED, MAIN_MENU -> {
                return chooseWhoAreYou(chatId, text, currentUser);
            }case SELL_MENU -> {
                return sellMenu(chatId,text,currentUser);
            }case CRUD_MENU -> {
                return CRUDMenu(chatId,text,currentUser);
            }case CHOSING_ONE_CAR -> {
               return chooseOneCar(chatId,text,currentUser);
            }case CREATECAR -> {
             return receiveCarInfo(chatId,text,currentUser);
            }case RECEIVING_CARPHOT0 -> {
             return receiveCarPhoto(message);
            }case UPDATE_CAR -> {
                return updateCarInfo(chatId,text,currentUser);
            }case FULL_UPDATE -> {
              return  fullUpdateCar(chatId,text,currentUser);
            }case RECIVINGPHOTOFORCURRENTCAR-> {
              return recivingPhotoForCurrentCar(message);
            }
        }
        return new SendMessage(chatId.toString(), "Wrong command or something went wrong 1");
    }


    public SendMessage handleStart(Long chatId, org.telegram.telegrambots.meta.api.objects.User from) {
        User user = null;
        try {
            user = userService.findByChatId(chatId);
            user.setState(UserState.MAIN_MENU);
            userService.update(user);
            user.setState(UserState.MAIN_MENU);
            SendMessage sendMessage = new SendMessage(chatId.toString(),
                    String.format("Welcome back %s, choose your role ", user.getName()));
            sendMessage.setReplyMarkup(buttons.getUserRole());
            return sendMessage;
        } catch (DataNotFoundException e) {
            log.info(e.getMessage(), chatId);
            User newUser = User.builder()
                    .name(from.getFirstName())
                    .username(from.getUserName())
                    .state(UserState.SHARE_CONTACT)
                    .chatId(chatId).build();
            user = userService.add(newUser);
        }
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                String.format("Welcome %s, please share your number\uD83D\uDCDE", user.getUsername()));
        sendMessage.setReplyMarkup(buttons.requestContact());
        return sendMessage;
    }

    public  SendMessage chooseWhoAreYou(Long chatId, String text, User currentUser){
        if (text.equals("SELLER\uD83E\uDD11")){
            SendMessage sendMessage = new SendMessage(chatId.toString(), "Welcome to Sell menu !!!");
            sendMessage.setReplyMarkup(buttons.getSellMenu());
            currentUser.setState(UserState.SELL_MENU);
            currentUser.setRole(UserRole.SELLER);
            userService.update(currentUser);
            return sendMessage;
        }else if(text.equals("Back➡\uFE0F")){
            SendMessage sendMessage = new SendMessage(chatId.toString(), "You return Main menu !!!");
            sendMessage.setReplyMarkup(buttons.getUserRole());
            currentUser.setState(UserState.MAIN_MENU);
            userService.update(currentUser);
            return sendMessage;
        } else if (text.equals("BUYER\uD83E\uDD20")) {
            SendMessage sendMessage = new SendMessage(chatId.toString(), "Please wait until i finish buyer side!!!");
            sendMessage.setReplyMarkup(buttons.getUserRole());
            currentUser.setState(UserState.MAIN_MENU);
            userService.update(currentUser);
            return sendMessage;
        }

        return new SendMessage(chatId.toString(),"Something went wrong 4 !!!");
    }

    public SendMessage sellMenu(Long chatId,String text,User currentUser){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(buttons.getUserRole());
        sendMessage.setChatId(chatId);
        currentUser.setState(UserState.MAIN_MENU);
        userService.update(currentUser);

        if (text.equals("Back➡\uFE0F")) {
            sendMessage.setReplyMarkup(buttons.getUserRole());
            currentUser.setState(UserState.MAIN_MENU);
            userService.update(currentUser);
            sendMessage.setText("You return Main menu !!!");
            return sendMessage;
        }

        if(text.equals("Show all\uD83D\uDC40")){
           return showAll(chatId,text,currentUser);
        }

        if(text.equals("CRUD car\uD83D\uDC4A")){
            return showCarForChoosing(chatId,text,currentUser);
        }
        return null;
    }


    public SendMessage CRUDMenu(Long chatId, String text, User currentUser){

        SendMessage sendMessage = new SendMessage(chatId.toString(), "You back to Main menu🙂");
        sendMessage.setReplyMarkup(buttons.getSellMenu());
        currentUser.setState(UserState.SELL_MENU);
        userService.update(currentUser);

        switch (text){
            case "Show\uD83D\uDC40" ->{
            return showCurrentCar(chatId,text,currentUser);
            }
            case "Create\uD83D\uDC4A" ->{
              return callCreateCar(chatId,text,currentUser);
            }case "Delete\uD83D\uDDD1" ->{
                return  deleteChosenCar(chatId,text,currentUser);
            }case "Update♻!!!" ->{
                return callUpdateCarInfo(chatId,text,currentUser);
            }case "Back➡\uFE0F" ->{
                return sendMessage;
            }default -> {
                sendMessage.setText("Something went wrong 2!!!");
                return sendMessage;
            }
        }
    }

    public SendMessage showCurrentCar(Long chatId,String text ,User currentUser) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());
        sendMessage.setChatId(chatId);
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);

        try {
            ArrayList<Photo> photos = photoService.findPhotoIdByCarId(currentCar.getId());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(currentCar.toString());
            sendPhoto.setPhoto(new InputFile(photos.getFirst().getPhotoId()));
            execute(sendPhoto);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException br ){
            sendMessage.setText(br.getMessage());
            send(sendMessage);
        } catch (NoSuchElementException g){
            sendMessage.setText("This kind of photo doesn't exist \uD83D\uDE1E❌ !!!");
        }catch (DataNotFoundException h){
            sendMessage.setText("This data didn't found ⁉\uFE0F");
        }catch (Exception u){
            sendMessage.setText(u.getMessage()+"\n"+u.getCause());
            send(sendMessage);
        }
        sendMessage.setText("Successfully done !!!");
        return sendMessage;
    }

    public SendMessage updateCarInfo(Long chatId,String text,User currentUser){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());
        sendMessage.setChatId(chatId);
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);
        try {

            if (text.equals("Back➡\uFE0F")) {
                sendMessage.setText("You return CRUD menu !!!");
                return sendMessage;
            } else if (text.equals("I sold car \uD83E\uDD1D!!!")) {
                currentCar.setStatus(CarStatus.SOLD);
                carService.update(currentCar);
                sendMessage.setText("Status changed \uD83D\uDC4D !!!");
                return sendMessage;
            } else if (text.equals("Full update \uD83D\uDD0D")) {
                sendMessage.setText("You must enter information like this don't add something from yourself !!!\n" +
                                "Model / madeBy / color / horsePower(hp) / engineVolume / year(2024-02-18) / runs / price / (SELLING or SOLD) / Phone number.\n" +
                                "Nexia / UzAvto / white / 102          / 1.6L atmosferni / 2014-10-09    / 150000 / 8000 / selling           / +998 97 123 45 67");
                sendMessage.setReplyMarkup(buttons.justBack());
                currentUser.setState(UserState.FULL_UPDATE);
                userService.update(currentUser);
               return sendMessage;
            }
        }catch (NullPointerException g){
                sendMessage.setText("Be attention and fill full fields !!!");
                return sendMessage;
            }catch (DataNotFoundException y){
            sendMessage.setText("This data didn't found ⁉\uFE0F");
            return sendMessage;
        }catch (Exception e){
                sendMessage.setText(e.getMessage() + e.getCause());
                return sendMessage;
            }
            sendMessage.setText("Wrong command or something 7 !!!");
            return sendMessage;
    }

    private SendMessage fullUpdateCar(Long chatId, String text, User currentUser) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());
        sendMessage.setChatId(chatId);
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);

        if (text.equals("Back➡\uFE0F")) {
            sendMessage.setText("You return CRUD menu !!!");
            return sendMessage;
        }
        try {
            String[] data = text.split("/");
            int runs = Integer.parseInt(data[6]);
            double price = Double.parseDouble(data[7]);
            CarStatus carStatus = CarStatus.valueOf(data[8].toUpperCase());
            carService.update(new Car(data[0], data[1], data[2], data[3], data[4], currentCar.getCreatedYear(), runs, price, currentCar.getOwnerId(), carStatus, data[9]));
            sendMessage.setText("Car succesfully created and now\nYou have to send your photo of car!!!");
            currentUser.setState(UserState.RECEIVING_CARPHOT0);
            userService.update(currentUser);
            sendMessage.setReplyMarkup(buttons.getCarPictureResponce());
            return sendMessage;
        }catch (NullPointerException g){
            sendMessage.setText("Be attention and fill full fields !!!");
            return sendMessage;
        }catch (Exception e){
            return new SendMessage(chatId.toString(),e.getMessage());
        }
    }

    public SendMessage recivingPhotoForCurrentCar(Message message){
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        User currentUser = userService.findByChatId(chatId);
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());

        if(Objects.isNull(message.getPhoto().getFirst().getFileId())){
            sendMessage.setText("You have to send only photo !!!");
            return sendMessage;
        }
        try {
        List<PhotoSize> photo = message.getPhoto();
        String photoId = photo.getLast().getFileId();

        photoService.add(new Photo(photoId,currentCar.getId()));
        }catch (Exception e){
            sendMessage.setText(e.getMessage() + "\t" + e.getCause());
            return sendMessage;
        }

        sendMessage.setText("Successfully created !!!");
        return sendMessage;
    }

    private SendMessage callUpdateCarInfo(Long chatId, String text, User currentUser) {
        SendMessage sendMessage = new SendMessage(chatId.toString(),"You have to choose one or back !!!");
        sendMessage.setReplyMarkup(buttons.updateButtons());
        currentUser.setState(UserState.UPDATE_CAR);
        userService.update(currentUser);
        return sendMessage;
    }

    public static SendMessage callCreateCar(Long chatId, String text, User currentUser) {
        String info = "You must enter information like this don't add something from yourself !!!\n" +
                "Model / madeBy / color / horsePower/ engineVolume / year(2024-02-18) / runs / price / (SELLING or SOLD) / Phone number.\n";
        SendMessage sendMessage = new SendMessage(chatId.toString(), info);
        currentUser.setState(UserState.CREATECAR);
        userService.update(currentUser);
        sendMessage.setReplyMarkup(buttons.justBack());
        return sendMessage;

    }

    public SendMessage receiveCarPhoto(Message message){
        Long chatId = message.getChatId();

        User currentUser = userService.findByChatId(chatId);
        ArrayList<Car> car = carService.findByOwnerId(currentUser.getId());
        Car car1 = car.getLast();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        currentUser.setState(UserState.CRUD_MENU);
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());
        userService.update(currentUser);

        if(Objects.isNull(message.getPhoto().getFirst().getFileId())){
            sendMessage.setText("You have to send only photo !!!");
            return sendMessage;
        }

        List<PhotoSize> photo = message.getPhoto();
        String photoId = photo.getFirst().getFileId();

        photoService.add(new Photo(photoId,car1.getId()));

        sendMessage.setText("Successfully created !!!");
        return sendMessage;
    }

    public SendMessage receiveCarInfo(Long chatId, String text, User currentUser) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());

        if(text.equals("Back➡\uFE0F")){
            sendMessage.setText("You return create CRUD menu ");
            return sendMessage;
        }
        try {
        String[] data = text.split("/");
        LocalDate year = LocalDate.parse(data[5]);
        int runs = Integer.parseInt(data[6]);
        double price = Double.parseDouble(data[7]);
        CarStatus carStatus = CarStatus.valueOf(data[8].toUpperCase());
        UUID ownerId = currentUser.getId();
        carService.add(new Car(data[0],data[1],data[2],data[3],data[4],year,runs,price,ownerId,carStatus,data[9]));
        currentCar = new Car(data[0],data[1],data[2],data[3],data[4],year,runs,price,ownerId,carStatus,data[9]);
            sendMessage.setText("Car succesfully created and now\nYou have to send your photo of car!!!");
            currentUser.setState(UserState.RECEIVING_CARPHOT0);
            userService.update(currentUser);
            sendMessage.setReplyMarkup(buttons.getCarPictureResponce());
            return sendMessage;
        }catch (NullPointerException g){
            sendMessage.setText("Be attention and fill full fields !!!");
            return sendMessage;
        }catch (Exception e){
            return new SendMessage(chatId.toString(),e.getMessage());
        }
    }

    public SendMessage showCarForChoosing(Long chatId,String text , User currentUser){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(buttons.justBack());
        currentUser.setState(UserState.CHOSING_ONE_CAR);
        userService.update(currentUser);

        if (text.equals("Back➡\uFE0F")) {

            sendMessage.setText("You return sell menu !!!");
            return sendMessage;
        }

        ArrayList<Car> byOwnerId = carService.findByOwnerId(currentUser.getId());
        int i=1;
        for (Car car : byOwnerId) {
            sendMessage.setText(i + "  -  " +  car.getModel() + "  --  "  + car.getCreatedYear());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
        sendMessage.setText("You have to choose one !!!");
        return sendMessage;
    }

    public SendMessage showAll(Long chatId,String text,User currentUser){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(buttons.getSellMenu());
        currentUser.setState(UserState.SELL_MENU);
        userService.update(currentUser);

        if (text.equals("Back➡\uFE0F")) {
            sendMessage.setText("You return CRUD menu !!!");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        try{
            ArrayList<Car> carList = carService.findByOwnerId(currentUser.getId());
            for (Car car : carList) {
            ArrayList<Photo> photos = photoService.findPhotoIdByCarId(car.getId());
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(car.toString());
            sendPhoto.setPhoto(new InputFile(photos.getFirst().getPhotoId()));
            execute(sendPhoto);
            }
        } catch (TelegramApiException e) {
            sendMessage.setText(e.getMessage()+"\n"+e.getCause());
            return sendMessage;
        } catch (InputMismatchException br ){
            sendMessage.setText(br.getMessage());
            return sendMessage;
        } catch (NoSuchElementException g){
            sendMessage.setText("This kind of photo doesn't exist \uD83D\uDE1E❌ !!!");
        }catch (DataNotFoundException h){
            sendMessage.setText("This data didn't found ⁉\uFE0F");
            return sendMessage;
        }catch (Exception u){
            sendMessage.setText(u.getMessage()+"\n"+u.getCause());
            return sendMessage;
        }
        sendMessage.setText("Successfully done !!!");
        return sendMessage;
    }

    public SendMessage chooseOneCar(Long chatId,String text , User currentUser){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);

        if (text.equals("Back➡\uFE0F")) {
            sendMessage.setReplyMarkup(buttons.getSellMenu());
            currentUser.setState(UserState.SELL_MENU);
            userService.update(currentUser);
            sendMessage.setText("You return Sell menu !!!");
            return sendMessage;
        }

        try{
        ArrayList<Car> carList = carService.findByOwnerId(currentUser.getId());
        Car car = carList.get(Integer.parseInt(text)-1);
          currentCar=car;
            sendMessage.setText("Successfully done !!!");
            return sendMessage;
        }catch (InputMismatchException br ){
            sendMessage.setText(br.getMessage());
            return sendMessage;
        } catch (NoSuchElementException g){
            sendMessage.setText("This kind of photo doesn't exist \uD83D\uDE1E❌ !!!");
        }catch (DataNotFoundException h){
            sendMessage.setText("This data didn't found ⁉\uFE0F");
        }catch (Exception u){
            sendMessage.setText(u.getMessage()+"\n"+u.getCause());
            return sendMessage;
        }
        return sendMessage;
    }

    public SendMessage deleteChosenCar(Long chatId,String text , User currentUser){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(buttons.getCRUDButtons());
        currentUser.setState(UserState.CRUD_MENU);
        userService.update(currentUser);

        if (text.equals("Back➡\uFE0F")) {
            sendMessage.setText("You return CRUD menu !!!");
            return sendMessage;
        }

        try{
            carService.delete(currentCar.getId());
        } catch (InputMismatchException br ){
            sendMessage.setText(br.getMessage());
            return sendMessage;
        } catch (NoSuchElementException g){
            sendMessage.setText("This kind of photo doesn't exist \uD83D\uDE1E❌ !!!");
            return sendMessage;
        }catch (DataNotFoundException h){
            sendMessage.setText("This data didn't found ⁉\uFE0F");
            return sendMessage;
        }catch (Exception u){
            sendMessage.setText(u.getMessage()+"\n"+u.getCause());
            return sendMessage;
        }
        sendMessage.setText("Successfully Deleted !!!");
        return sendMessage;
    }

    public void send(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
