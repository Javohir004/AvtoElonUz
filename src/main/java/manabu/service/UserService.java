package manabu.service;
import manabu.Repository.UserRepository;
import manabu.exception.DataNotFoundException;
import manabu.model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


public class UserService extends BaseService<User, UserRepository> {

    private static final UserService userService = new UserService();

    private UserService() {
        super(new UserRepository());

    }

    public static UserService getInstance(){
        return userService;
    }

    public User findByChatId(Long chatId) {
        Optional<User> userOptional = repository.findByChatId(chatId);

        return userOptional.orElseThrow(() -> {
            return new DataNotFoundException("user with this chat {} is not found");
        });
    }


    public void update(User updated) {
        ArrayList<User> all = repository.getAll();
        Integer i = 0;
        for (User user : all) {
            if (Objects.equals(user.getId(), updated.getId())) {
                all.set(i, updated);
                break;
            }
            i++;
        }
        repository.writeData(all);
    }

}
