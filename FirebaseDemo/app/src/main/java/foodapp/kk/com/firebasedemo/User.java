package foodapp.kk.com.firebasedemo;



/**
 * Created by Sairam on 4/8/2016.
 */
public class User {
    private int birthYear;
    private String fullName;

    public User() {}

    public User(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "User{" +
                "birthYear=" + birthYear +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public long getBirthYear() {
        return birthYear;
    }

    public String getFullName() {
        return fullName;
    }
}

