package manabu;

import manabu.bot.MyBot;
import manabu.enomerator.CarStatus;
import manabu.model.Car;
import manabu.service.CarService;
import manabu.service.UserService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;

import static manabu.bot.MyBot.carService;

public class Main {
    public static void main(String[] args) {
      ///  cars();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot());
            System.out.println("Bot started");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

//    private static void cars() {
//        carService.add(new Car("M5","BMW","Blue","525 hp","4.5L V8 turbo",LocalDate.parse("2024-01-12"),1000,100000,null, CarStatus.SELLING,null));
//        carService.add(new Car("Helcat","Dodge","Dark","500 hp","4.0L V12 turbo", LocalDate.parse("2022-06-05"),0,150000,null,CarStatus.SELLING,null));
//        carService.add(new Car("RS8","AUDI","Blue","520 hp","4.0L V8 turbo", LocalDate.parse("2023-07-07"),1000,100000,null,CarStatus.SELLING,null));
//        carService.add(new Car("ClS 67","Mersedes Ben","White","300 hp","2.5L V8 turbo", LocalDate.parse("2022-04-18"),1000,100000,null,CarStatus.SELLING,null));
//        carService.add(new Car("Camaro","Chevrolet","Red","400 hp","4.5L V8 turbo", LocalDate.parse("2024-06-22"),1000,100000,null,CarStatus.SELLING,null));
//    }

}
