import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>(); // Part 3 requirement
    private static List<Message> disregardedMessages = new ArrayList<>(); // Part 3 requirement
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

        // ===== PART 2 + 3: Messaging Menu =====
        messagingMenu();
    }

    private static void messagingMenu() {
        while (true) {
            System.out.println("\n=== QuickChat Menu ===");
            System.out.println("1. Send Message");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Show Total Messages Sent");
            System.out.println("4. Stored Messages"); // NEW Part 3
            System.out.println("5. Exit"); // shifted from 4 to 5
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
                case 4 -> storedMessagesMenu(); // NEW Part 3
                case 5 -> {
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

        String lengthCheck = msg.checkMessageLength();
        if (!lengthCheck.equals("Message ready to send.")) {
            System.out.println(lengthCheck);
            return;
        }

        String recipientCheck = msg.checkRecipientCell();
        if (!recipientCheck.equals("Cell phone number successfully captured.")) {
            System.out.println(recipientCheck);
            return;
        }

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
        } else if (option == 2) {
            disregardedMessages.add(msg);
        } else if (option == 3) {
            storedMessages.add(msg);
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

    // ===== PART 3: Stored Messages Menu + Methods =====
    private static void storedMessagesMenu() {
        loadStoredMessages();

        while (true) {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("1. Display sender & recipient of all stored messages");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search by Message ID");
            System.out.println("4. Search by Recipient");
            System.out.println("5. Delete message by Hash");
            System.out.println("6. Display full report");
            System.out.println("0. Back to main menu");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1 -> displaySenderRecipient();
                case 2 -> displayLongestStored();
                case 3 -> searchByMessageID();
                case 4 -> searchByRecipient();
                case 5 -> deleteByHash();
                case 6 -> displayReport();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // a. Display sender + recipient
    private static void displaySenderRecipient() {
        if(storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.println("\n--- All Stored Messages ---");
        for(Message m : storedMessages) {
            System.out.println("Sender: Developer | Recipient: " + m.getRecipient());
        }
    }

    // b. Longest stored message
    private static void displayLongestStored() {
        if(storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        Message longest = storedMessages.get(0);
        for(Message m : storedMessages) {
            if(m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        System.out.println("\nLongest stored message: \"" + longest.getMessageText() + "\"");
    }

    // c. Search by Message ID
    private static void searchByMessageID() {
        System.out.print("Enter Message ID: ");
        String id = sc.nextLine();
        for(Message m : storedMessages) {
            if(m.getMessageID().equals(id)) {
                System.out.println("Recipient: " + m.getRecipient());
                System.out.println("Message: " + m.getMessageText());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    // d. Search by Recipient - checks Sent + Stored
    private static void searchByRecipient() {
        System.out.print("Enter Recipient number: ");
        String rec = sc.nextLine();
        boolean found = false;
        for(Message m : sentMessages) {
            if(m.getRecipient().equals(rec)) {
                System.out.println("\"" + m.getMessageText() + "\"");
                found = true;
            }
        }
        for(Message m : storedMessages) {
            if(m.getRecipient().equals(rec)) {
                System.out.println("\"" + m.getMessageText() + "\"");
                found = true;
            }
        }
        if(!found) System.out.println("No messages found for that recipient.");
    }

    // e. Delete by Hash
    private static void deleteByHash() {
        System.out.print("Enter Message Hash: ");
        String hash = sc.nextLine();
        for(int i = 0; i < storedMessages.size(); i++) {
            if(storedMessages.get(i).getMessageHash().equals(hash)) {
                System.out.println("Message: \"" + storedMessages.get(i).getMessageText() + "\" successfully deleted.");
                storedMessages.remove(i);
                return;
            }
        }
        System.out.println("Hash not found.");
    }

    // f. Display Report - Sent + Stored
    private static void displayReport() {
        System.out.println("\n=== FULL MESSAGE REPORT ===");
        for(Message m : sentMessages) {
            System.out.println("Hash: " + m.getMessageHash());
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessageText());
            System.out.println("------------------------");
        }
        for(Message m : storedMessages) {
            System.out.println("Hash: " + m.getMessageHash());
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessageText());
            System.out.println("------------------------");
        }
    }

    // Load from messages.json
    private static void loadStoredMessages() {
        storedMessages.clear();
        try {
            if(!Files.exists(Paths.get("messages.json"))) return;

            List<String> lines = Files.readAllLines(Paths.get("messages.json"));
            for(String line : lines) {
                if(!line.trim().isEmpty()) {
                    JSONObject json = new JSONObject(line);
                    Message m = new Message(json.getString("recipient"), json.getString("message"));
                    // Add setters to Message class for this to work
                    m.setMessageID(json.getString("messageID"));
                    m.setMessageHash(json.getString("messageHash"));
                    storedMessages.add(m);
                }
            }
        } catch(Exception e) {
            System.out.println("Error loading stored messages: " + e.getMessage());
        }
    }
}