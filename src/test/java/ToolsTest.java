import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

public class ToolsTest {


    //Testing email verifier
    @Test
    @DisplayName("Test of email checker, valid argument.")
    public void testPatternMatchesNoException(){
        String email = "alphone.daudet@gmail.com";
        Assert.assertTrue(Tools.patternMatches(email));
    }

    @Test
    @DisplayName("Test of email checker, invalid argument.")
    public void testPatternMatchesThrowsException(){
        String email = ".alphone.daudet@gmail.com";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Tools.patternMatches(email));
    }

    //Testing length checking method
    @Test
    @DisplayName("Test of length checking method, valid argument.")
    public void testCheckLengthNoException(){
        String string = "test";
        int length = 10;
        Assert.assertTrue(Tools.checkLength(string, 10));
    }

    @Test
    @DisplayName("Test of length checking method, invalid(too long) argument.")
    public void testCheckLengthTooLongException(){
        String string = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        int length = 0;
        Assertions.assertThrows(IllegalArgumentException.class,() ->
                Tools.checkLength(string, length));
    }

    @Test
    @DisplayName("Test of length checking method, invalid(too short) argument.")
    public void testCheckLengthTooShortException(){
        String string = "";
        int length = 10;
        Assertions.assertThrows(IllegalArgumentException.class,() ->
                Tools.checkLength(string, 10));
    }

    //Testing blanks checking method
    @Test
    @DisplayName("Test of blank checking method, valid argument.")
    public void testCheckBlanksNoException(){
        String string = "string";
        Assert.assertFalse(Tools.checkBlanks(string));
    }

    @Test
    @DisplayName("Test of blank checking method, invalid(has blanks) argument.")
    public void testCheckLengthHasBlanksException(){
        String string = "    aze";
        Assertions.assertThrows(IllegalArgumentException.class,() ->
                Tools.checkBlanks(string));
    }

    //Testing string validity method
    @Test
    @DisplayName("Test of validity checking method, valid arguments.")
    public void testCheckValidStringNoException(){
        String string = "test";
        int length = 10;
        Assert.assertTrue(Tools.checkValidString(string, length));
    }

    @Test
    @DisplayName("Test of validity checking method, invalid arguments.")
    public void testCheckValidStringIllegalArgumentException(){
        String string = "test";
        int length = 0;
        Assertions.assertThrows(IllegalArgumentException.class,() ->
                Tools.checkValidString(string, length));
    }

    //Testing special character method
    @Test
    @DisplayName("Test of special characters checking method, valid arguments.")
    public void testCheckSpecialCharactersNoException(){
        String string = "azomdkaozfkmao";
        Assert.assertTrue(Tools.checkSpecialCharacters(string));
    }

    @Test
    @DisplayName("Test of special characters checking method, invalid arguments.")
    public void testCheckSpecialCharactersThrowsException(){
        String string = "%âzdoazm!!:";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Tools.checkSpecialCharacters(string));
    }

    //Testing digit checking method
    @Test
    @DisplayName("Test of digit checking method, valid arguments.")
    public void testCheckDigitsNoException(){
        String string = "azdazdazdazd";
        Assert.assertFalse(Tools.checkDigits(string));
    }

    @Test
    @DisplayName("Test of digit checking method, invalid arguments.")
    public void testCheckDigitsThrowsException(){
        String string = "addazdazd546584";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Tools.checkDigits(string));
    }


    //Testing friend list check
    @Test
    @DisplayName("Test of friend list checking method, valid arguments.")
    public void testOnFriendListNoException(){
        User mainUser = new User("marco","marco@hotmail.com", "marc", "paul");
        User user = new User("polo", "polo@hotmail.com");
        Assertions.assertFalse(Tools.onFriendList(mainUser, user));
    }

    @Test
    @DisplayName("Test of friend list checking method, invalid argument.")
    public void testOnFriendListIllegalArgumentException(){
        User mainUser = new User("marco","marco@hotmail.com", "marc", "paul");
        User user = new User("polo", "polo@hotmail.com");
        mainUser.getFriendList().add(user);
        Assertions.assertTrue(Tools.onFriendList(mainUser, user));
    }

    @Test
    @DisplayName("Test of denied list checking method, valid arguments.")
    public void testOnDeniedListNoException(){
        User mainUser = new User("marco","marco@hotmail.com", "marc", "paul");
        User user = new User("polo", "polo@hotmail.com");
        Assertions.assertFalse(Tools.onDeniedList(mainUser, user));
    }

    @Test
    @DisplayName("Test of denied list checking method, invalid arguments.")
    public void testOnDeniedListIllegalArgumentException(){
        User mainUser = new User("marco","marco@hotmail.com", "marc", "paul");
        User user = new User("polo", "polo@hotmail.com");
        mainUser.getDeniedList().add(user);
        Assertions.assertTrue(Tools.onDeniedList(mainUser, user));
    }

    //Testing isOnList method
    @Test
    @DisplayName("Test of isOnList method, valid arguments.")
    public void testIsOnListNoException(){
        User mainUser = new User("marco","marco@hotmail.com", "marc", "paul");
        User user = new User("polo", "polo@hotmail.com");
        Assertions.assertFalse(Tools.isOnList(mainUser, user));
    }
}
