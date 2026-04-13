import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PASSWORDTest {
    PASSWORD password = new PASSWORD();

    @Test
    void testCheckUserName_CorrectFormat() {
        assertTrue(password.checkUserName("kyl_1"), "Username with _ and <=5 chars should be valid");
    }

    @Test
    void testCheckUserName_IncorrectFormat() {
        assertFalse(password.checkUserName("kyle!!!!!!!"), "Username without _ or >5 chars should be invalid");
    }

    @Test
    void testCheckPasswordComplexity_MeetsRequirements() {
        assertTrue(password.checkPasswordComplexity("Ch&sec@ke99!"), "Password meets all requirements");
    }

    @Test
    void testCheckPasswordComplexity_DoesNotMeetRequirements() {
        assertFalse(password.checkPasswordComplexity("password"), "Password missing caps, number, special char");
    }

    @Test
    void testCheckCellPhoneNumber_CorrectFormat() {
        assertTrue(password.checkCellPhoneNumber("+27838968976"), "Correct SA format with +27");
    }

    @Test
    void testCheckCellPhoneNumber_IncorrectFormat() {
        assertFalse(password.checkCellPhoneNumber("08966553"), "Incorrect format missing +27 and too short");
    }
}