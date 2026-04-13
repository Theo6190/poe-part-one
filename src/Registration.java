/**
 * Registration class
 * Stores user details and returns registration messaging
 */
public class Registration {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;
    private PASSWORD validator;

    public Registration() {
        this.validator = new PASSWORD();
    }

    public String registerUser(String firstName, String lastName, String username, String password, String cellNumber) {
        if (!validator.checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!validator.checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!validator.checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;

        return "User registered successfully.";
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}