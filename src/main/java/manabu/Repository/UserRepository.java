package manabu.Repository;

import manabu.model.User;

import java.util.Objects;
import java.util.Optional;

public class UserRepository extends BaseRepository<User> {


    public UserRepository() {
        super.path = "src/main/resources/users.json";
        super.type = User.class;
    }


    public Optional<User> findByChatId(Long chatId) {
        return getAll().stream().filter((user) -> Objects.equals(user.getChatId(), chatId)).findFirst();
    }

}
