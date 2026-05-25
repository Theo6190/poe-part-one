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

    public String createMessageHash() {
        String idPart = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return idPart + ":" + (messageText.length() % 10) + ":" + firstWord + lastWord;
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

    // Add these 4 getters below
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
}