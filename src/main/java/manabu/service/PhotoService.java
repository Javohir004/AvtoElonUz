package manabu.service;

import manabu.Repository.PhotoRepository;
import manabu.model.Photo;

import java.util.ArrayList;
import java.util.UUID;

public class PhotoService extends BaseService<Photo, PhotoRepository>{
    public PhotoService(PhotoRepository repository) {
        super(repository);
    }


    public ArrayList<Photo> findPhotoIdByCarId(UUID id){
        return repository.getPhotoIdByCarId(id);
    }

}
