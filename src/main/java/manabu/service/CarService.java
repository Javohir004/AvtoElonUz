package manabu.service;

import manabu.Repository.CarRepository;
import manabu.exception.DataNotFoundException;
import manabu.model.Car;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CarService extends BaseService<Car, CarRepository> {

    private static final CarService carService = new CarService();

    private CarService() {
        super(new CarRepository());
    }

    public static CarService getInstance() {
        return carService;
    }

    public void update(Car updated) {
        ArrayList<Car> all = repository.getAll();
        Integer i = 0;
        for (Car car : all) {
            if(Objects.equals(car.getOwnerId(),updated.getOwnerId())){
                updated.setId(car.getId());
                all.set(i,updated);
                repository.writeData(all);
                return;
            }
            i++;
        }
    }

    public ArrayList<Car> findByOwnerId(UUID id) {
        return repository.findByOwnerId(id);
    }

}
