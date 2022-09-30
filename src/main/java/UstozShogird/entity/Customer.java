package UstozShogird.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String chatId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String confirmPassword;

}
