import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    PASSWORD password = new PASSWORD();

    @Test
    void testCheckUserName_CorrectFormat() {
        assertTrue(password.checkUserName("kyl_1"));
    }

    @Test
    void testCheckUserName_IncorrectFormat() {
        assertFalse(password.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void testCheckPasswordComplexity_MeetsRequirements() {
        assertTrue(password.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void testCheckPasswordComplexity_DoesNotMeetRequirements() {
        assertFalse(password.checkPasswordComplexity("password"));
    }

    @Test
    void testCheckCellPhoneNumber_CorrectFormat() {
        assertTrue(password.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void testCheckCellPhoneNumber_IncorrectFormat() {
        assertFalse(password.checkCellPhoneNumber("08966353"));
    }
}