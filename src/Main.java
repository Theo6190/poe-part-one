import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    private static List<Message> sentMessages = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // ===== PART 1: Registration & Login =====
        Registration reg = new Registration();
        Login login = new Login();

        System.out.println("=== QuickChat Registration ===");
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter cell number +27XXXXXXXXX: ");
        String cell = sc.nextLine();

        String regMessage = reg.registerUser(firstName, lastName, username, password, cell);
        System.out.println(regMessage);

        if (!regMessage.equals("User registered successfully.")) {
            System.out.println("Exiting program.");
            return;
        }

        System.out.println("\n=== QuickChat Login ===");
        System.out.print("Enter username: ");
        String loginUser = sc.nextLine();

        System.out.print("Enter password: ");
        String loginPass = sc.nextLine();

        String loginMessage = login.returnLoginStatus(reg, loginUser, loginPass);
        System.out.println(loginMessage);

        if (!loginMessage.startsWith("Welcome")) {
            System.out.println("Exiting program.");
            return;
        }

        // ===== PART 2: Messaging Menu =====
        messagingMenu();
    }

    private static void messagingMenu() {
        while (true) {
            System.out.println("\n=== QuickChat Menu ===");
            System.out.println("1. Send Message");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Show Total Messages Sent");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> sendMessageFlow();
                case 2 -> printMessages();
                case 3 -> System.out.println("Total messages sent: " + Message.returnTotalMessages());
                case 4 -> {
                    System.out.println("Exiting QuickChat. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void sendMessageFlow() {
        System.out.print("Enter recipient number: ");
        String recipient = sc.nextLine();

        System.out.print("Enter message: ");
        String text = sc.nextLine();

        Message msg = new Message(recipient, text);

        // Check message length first
        String lengthCheck = msg.checkMessageLength();
        if (!lengthCheck.equals("Message ready to send.")) {
            System.out.println(lengthCheck);
            return;
        }

        // Check recipient format
        String recipientCheck = msg.checkRecipientCell();
        if (!recipientCheck.equals("Cell phone number successfully captured.")) {
            System.out.println(recipientCheck);
            return;
        }

        // Send/Store/Disregard options
        System.out.println("\n1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");
        System.out.print("Choose option: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        int option = sc.nextInt();
        sc.nextLine();

        String result = msg.sentMessage(option);
        System.out.println(result);

        if (option == 1) {
            sentMessages.add(msg);
            printMessageDetails(msg);
        } else if (option == 3) {
            storeMessage(msg);
        }
    }

    private static void printMessageDetails(Message msg) {
        System.out.println("\n--- Message Details ---");
        System.out.println("Message ID: " + msg.getMessageID());
        System.out.println("Message Hash: " + msg.getMessageHash());
        System.out.println("Recipient: " + msg.getRecipient());
        System.out.println("Message: " + msg.getMessageText());
    }

    private static void printMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
            return;
        }
        for (Message m : sentMessages) {
            printMessageDetails(m);
        }
    }

    private static void storeMessage(Message msg) {
        JSONObject json = new JSONObject();
        json.put("messageID", msg.getMessageID());
        json.put("messageHash", msg.getMessageHash());
        json.put("recipient", msg.getRecipient());
        json.put("message", msg.getMessageText());

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json.toString() + "\n");
            System.out.println("Message stored in messages.json");
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}