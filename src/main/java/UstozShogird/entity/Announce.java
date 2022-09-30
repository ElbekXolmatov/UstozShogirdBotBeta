package UstozShogird.entity;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Announce {
    private String id;
    private String fullName;
    private String technology;
    private int age;
    private String phoneNumber;
    private String price;
    private String place;
    private String job;
    private String timeToCall;
    private String purpose;
    private String organization;
    private String workDurability;
    private String additional;
    private String userName;

    public Announce(String id, String fullName, String technology, String phoneNumber, String place, String price,  String job, String timeToCall, String purpose) {
        this.id = id;
        this.fullName = fullName;
        this.technology = technology;
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.price = price;
        this.job = job;
        this.timeToCall = timeToCall;
        this.purpose = purpose;
    }

    public Announce(String id, String fullName, int age,
                    String technology,  String phoneNumber, String place, String price,  String job, String timeToCall, String purpose) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.technology = technology;
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.price = price;
        this.job = job;
        this.timeToCall = timeToCall;
        this.purpose = purpose;
    }

    public Announce(String id, String organization, String technology,
                    String phoneNumber,String place, String fullName, String timeToCall, String workDurability, String price, String additional) {
        this.id = id;
        this.organization = organization;
        this.technology = technology;
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.fullName = fullName;
        this.timeToCall = timeToCall;
        this.workDurability = workDurability;
        this.price = price;
        this.additional = additional;
    }
}
