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
        String userMsg = validator.getUserNameMessage(username);
        if (!userMsg.equals("Username successfully captured.")) return userMsg;

        String passMsg = validator.getPasswordMessage(password);
        if (!passMsg.equals("Password successfully captured.")) return passMsg;

        String cellMsg = validator.getCellMessage(cellNumber);
        if (!cellMsg.equals("Cell number successfully captured.")) return cellMsg;

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