import java.util.Scanner;

/**
 * Main class
 * Handles user input/output and runs registration + login flow
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Registration reg = new Registration();
        Login login = new Login();
        PASSWORD pass = new PASSWORD();

        // Registration
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Enter cell number: ");
        String cell = input.nextLine();

        String regMessage = reg.registerUser(firstName, lastName, username, password, cell);
        System.out.println(regMessage);

        // Only proceed to Login  if registration successful
        if (regMessage.equals("User registered successfully.")) {
            System.out.println("\n--- Login ---");
            System.out.print("Enter username: ");
            String loginUser = input.nextLine();
            System.out.print("Enter password: ");
            String loginPass = input.nextLine();

            String loginMessage = login.returnLoginStatus(reg, loginUser, loginPass);
            System.out.println(loginMessage);
        }

        input.close();
    }
}