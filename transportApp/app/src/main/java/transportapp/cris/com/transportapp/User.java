package transportapp.cris.com.transportapp;

/**
 * Created by Cristiana on 1/9/2016.
 */
public class User {

    private String firstName, lastName, emailAdr, password;

    public User(String firstName, String lastName, String emailAdr, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdr = emailAdr;
        this.password = password;
    }

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAdr() {
        return emailAdr;
    }

    public void setEmailAdr(String emailAdr) {
        this.emailAdr = emailAdr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Nume = " + firstName + " Prenume = " + lastName + " Password = " + password + " Email = " + emailAdr;
    }
}
