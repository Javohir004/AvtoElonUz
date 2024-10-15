package manabu.service;

import manabu.Repository.BaseRepository;
import manabu.model.BaseModel;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseService <T extends BaseModel , R extends BaseRepository<T>> {
    protected R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public T add(T t){
       return repository.save(t);
    }

    public void delete(UUID id){
        repository.remove(id);
    }

//    public void update(UUID id, T t){
//        repository.update(id,t);
//    }

    public Optional<T> findById(UUID id){
        return repository.findById(id);
    }

    public ArrayList<T> getAll() {
        return repository.getAll();
    }
}
