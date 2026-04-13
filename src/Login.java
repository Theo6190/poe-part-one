/**
 * Login class
 * Handles user authentication and login status messages
 */
public class Login {

    public Boolean loginUser(Registration registeredUser, String enteredUsername, String enteredPassword) {
        if (registeredUser.getUsername() == null || registeredUser.getPassword() == null) {
            return false;
        }
        return enteredUsername.equals(registeredUser.getUsername()) &&
                enteredPassword.equals(registeredUser.getPassword());
    }

    public String returnLoginStatus(Registration registeredUser, String enteredUsername, String enteredPassword) {
        if (loginUser(registeredUser, enteredUsername, enteredPassword)) {
            return "Welcome " + registeredUser.getFirstName() + ", " + registeredUser.getLastName() + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}