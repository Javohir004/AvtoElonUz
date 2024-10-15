package manabu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import manabu.enomerator.CarStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car extends BaseModel{

    private String model;
    private String madeBy;
    private String color;
    private String horsePower;
    private String engineV;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdYear;
    private Integer runs;
    private double price;
    private UUID ownerId;
    private CarStatus status;
    private String ownerPhoneNum;

    @Override
    public String toString() {
        return "model⚛ \uFE0F : " + model +
                "\nmadeBy \uD83C\uDFE2 : " + madeBy +
                "\ncolor \uD83C\uDFA8 : " + color +
                "\nhorsePower \uD83D\uDC0E : " + horsePower + " Hp" +
                "\nengineV⚙ \uFE0F : " + engineV +
                "\ncreatedYear ⏳: " + createdYear + " year" +
                "\nruns \uD83D\uDEE3 : " + runs + " km" +
                "\nprice \uD83D\uDCB0 : " + price + " \uD83D\uDCB5" +
                "\non selling \uD83D\uDCE3 :" + status +
                 "\nContact \uD83D\uDCDE : " + ownerPhoneNum;
    }
}
