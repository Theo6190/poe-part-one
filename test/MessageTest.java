import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private Message validMessage;
    private Message longMessage;
    private Message invalidRecipientMessage;

    @BeforeEach
    void setUp() {
        validMessage = new Message("+27123456789", "Hello world");
        longMessage = new Message("+27123456789", "A".repeat(300));
        invalidRecipientMessage = new Message("12345", "Test message");
    }

    @Test
    void testCheckRecipientCellValid() {
        assertEquals("Cell phone number successfully captured.", validMessage.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCellInvalid() {
        assertEquals("Cell phone number incorrectly formatted.", invalidRecipientMessage.checkRecipientCell());
    }

    @Test
    void testCheckMessageLengthValid() {
        assertEquals("Message ready to send.", validMessage.checkMessageLength());
    }

    @Test
    void testCheckMessageLengthTooLong() {
        assertEquals("Message exceeds 250 characters.", longMessage.checkMessageLength());
    }

    @Test
    void testCreateMessageHashFormat() {
        String hash = validMessage.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.matches("\\d{2}:\\d:[A-Z]+[A-Z]+"));
    }

    @Test
    void testSentMessageOption1IncrementsTotal() {
        int before = Message.returnTotalMessages();
        String response = validMessage.sentMessage(1);
        assertEquals("Message successfully sent.", response);
        assertEquals(before + 1, Message.returnTotalMessages());
    }

    @Test
    void testSentMessageOption2() {
        assertEquals("Message disregarded.", validMessage.sentMessage(2));
    }

    @Test
    void testSentMessageOption3() {
        assertEquals("Message stored.", validMessage.sentMessage(3));
    }

    @Test
    void testSentMessageInvalidOption() {
        assertEquals("Invalid option.", validMessage.sentMessage(99));
    }

    @Test
    void testGetters() {
        assertNotNull(validMessage.getMessageID());
        assertEquals("+27123456789", validMessage.getRecipient());
        assertEquals("Hello world", validMessage.getMessageText());
        assertNotNull(validMessage.getMessageHash());
    }
}
