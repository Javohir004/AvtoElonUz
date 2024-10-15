package manabu.model;

import lombok.*;
import manabu.enomerator.UserRole;
import manabu.enomerator.UserState;

import org.telegram.telegrambots.meta.api.objects.Location;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User extends BaseModel {

    private String name;
    private String username;
    private String phoneNumber;
    private Location location;
    private UserRole role;
    private UserState state;
    private Long chatId;


}
