package manabu.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import manabu.model.BaseModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseRepository<T extends BaseModel> {

    protected String path;
    protected Class<T> type;

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);



    public T save(T t){
        ArrayList<T> data = readFromFile();
        data.add(t);
        writeData(data);
        return t;
    }

    public void remove(UUID id){
        ArrayList<T> data = readFromFile();
        for (T datum : data) {
            if( datum.isActive() && datum.getId().equals(id)){
                data.remove(datum);
        writeData(data);
        break;
            }
        }
    }




//    /** shuni to'liq qilish kerak **/
//    public void update(UUID id , T t) {
//        ArrayList<T> data = readFromFile();
//        for (T datum : data) {
//            if( datum.isActive() && datum.getId().equals(id)){
//
//            }
//        }
//    }



    public void writeData(ArrayList<T> data){
        try {
            objectMapper.writeValue(new File(path),data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ArrayList<T> readFromFile(){
        try {
         return objectMapper.readValue(new File(path), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<T> findById(UUID id){
    ArrayList<T> data = readFromFile();
        for (T datum : data) {
            if( datum.isActive() && datum.getId().equals(id)){
                return Optional.of(datum);
            }
        }
        return Optional.empty();
    }


    public ArrayList<T> getAll(){
        return readFromFile();
    }
}
