package manabu.Repository;

import manabu.model.Car;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CarRepository extends BaseRepository<Car> {

   public CarRepository() {
        super.path="src/main/resources/cars.json";
        super.type=Car.class;
    }



//    public Optional<Car> findByOwnerId(UUID ownerId){
//       return getAll().stream().filter((car -> car.isActive() && Objects.equals(car.getOwnerId(),ownerId))).findAny();
//    }

    public ArrayList<Car> findByOwnerId(UUID ownerId){
       return getAll().stream().filter((car -> car.isActive() && Objects.equals(car.getOwnerId(),ownerId)))
               .collect(Collectors.toCollection(ArrayList::new));
    }
}
