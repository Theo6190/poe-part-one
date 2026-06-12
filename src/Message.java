import java.util.Random;

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessagesSent = 0;

    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000L + (long)(rand.nextDouble() * 9000000L);
        return String.valueOf(id);
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number incorrectly formatted.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Message exceeds 250 characters.";
        }
    }

    // Updated to POE standard: First 10 chars + Last 3 of ID + Last 2 of recipient
    public String createMessageHash() {
        String first10 = messageText.length() >= 10 ?
                messageText.substring(0,10).toUpperCase() :
                messageText.toUpperCase();

        String last3ID = messageID.substring(messageID.length() - 3);

        String last2Rec = recipient.substring(recipient.length() - 2).toUpperCase();

        return first10 + ":" + last3ID + ":" + last2Rec;
    }

    public String sentMessage(int option) {
        if (option == 1) {
            totalMessagesSent++;
            return "Message successfully sent.";
        } else if (option == 2) {
            return "Message disregarded.";
        } else if (option == 3) {
            return "Message stored.";
        }
        return "Invalid option.";
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    // NEW: Setters needed for loading from JSON in Part 3
    public void setMessageID(String id) {
        this.messageID = id;
    }

    public void setMessageHash(String hash) {
        this.messageHash = hash;
    }
}