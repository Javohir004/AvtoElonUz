package manabu.Repository;

import manabu.model.Photo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PhotoRepository extends BaseRepository<Photo>{

    //    public UserRepository() {
    //        super.path = "src/main/resources/users.json";
    //        super.type = User.class;
    //    }//
    public PhotoRepository(){
        super.path = "src/main/resources/photo.json";
        super.type= Photo.class;
    }

    public ArrayList<Photo> getPhotoIdByCarId(UUID id){
        return getAll().stream()
                .filter(photo -> Objects.equals(photo.getOwnerId(), id))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
