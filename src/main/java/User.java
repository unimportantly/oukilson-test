import java.awt.*;
import java.sql.Blob;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class User {

    public static boolean patternMatches(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }
    private int userId;
    private String password;
    private String firstName;
    private String lastName;
    private String email; //TODO test unicité du mail
    private String nickname; //TODO test unicité du nickname
    private Blob icon;
    private List<Token> token;
    private List<User> friendList;
    private List<User> deniedList;
    private List<Game> userGameList;
    private List<Game> userLikeList;
    /**
     * empty constructor
     */
    public User() {
    }

    /**
     * nickname only constructor
     * @param nickname user input nickname
     * @throws IllegalArgumentException if the nickname is either too short or too long or contains illegal characters
     */
    public User(String nickname) throws IllegalArgumentException{
        this.setNickname(nickname);
    }

    /**
     * constructor with nick & email, the two basic parameters at account creation
     * @param nickname user chosen nickname
     * @param email user's email address
     * @throws IllegalArgumentException if the nickname/email is either too short or long or invalid etc
     */
    public User(String nickname,String email) throws IllegalArgumentException{
        this(nickname);
        this.setEmail(email);
    }

    /**
     * constructor with 3 arguments
     * @param firstName user's first name, can be null
     * @param email user's email
     * @param nickname user's nickname
     * @throws IllegalArgumentException too long/short, invalid input
     */
    public User(String nickname, String email, String firstName) throws IllegalArgumentException{
        this(nickname, email);
        this.setFirstName(firstName);
    }

    /**
     * constructor with 4 arguments, instantiates lists
     * @param nickname user's nickname
     * @param email user's email
     * @param firstName user's first name, can be null
     * @param lastName user's last name, can be null
     * @throws IllegalArgumentException too long or too short or invalid input
     */
    public User(String nickname, String email, String firstName, String lastName) throws IllegalArgumentException{
        this(nickname, email, firstName);
        this.setLastName(lastName);
        this.friendList = new ArrayList<User>();
        this.deniedList = new ArrayList<User>();
        this.userGameList = new ArrayList<Game>();
        this.userLikeList = new ArrayList<Game>();
    }

     /**
     * full (?) constructor
     * @param userId PK
     * @param password encrypted, 40 chars long
     * @param firstName user's first name, 45 chars
     * @param lastName user's last name, 45 chars, increase to 100 in db
     * @param email user's email, 45 chars, increase to 100 in db
     * @param nickname user's nickname, 45 chars
     * @param icon user icon, can be a default, size limit?
     * @param token user's token, stored in a list, can be null or many, no limit (total number of tokens)
     * @param friendList user's friends, have privileged access to events, can be null, no limit (total number of users)
     * @param deniedList user's pick of people they don't want to associate with, can be null, no limit (total number of users)
     * @param userGameList user's owned games, stored in a list, can be null or many, no limit (total number of games)
     * @param userLikeList user's liked games, stored in a list, can be null or many, no limit (total number of games)
     */
    public User(int userId, String password, String firstName, String lastName, String email, String nickname, Blob icon,
                 List<Token> token, List<User> friendList, List<User> deniedList, List<Game> userGameList,
                List<Game> userLikeList) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.nickname = nickname;
        this.icon = icon;
        this.token = token;
        this.friendList = friendList;
        this.deniedList = deniedList;
        this.userGameList = userGameList;
        this.userLikeList = userLikeList;
    }


    //methods
    public void addUserToFriendList(User user) throws IllegalArgumentException{
        if(!Tools.isOnList(this, user)) {
            this.getFriendList().add(user);
        }
        else {
            throw new IllegalArgumentException("user already friended");
        }
        this.setFriendList(this.getFriendList());
    }

    public void addUserToDeniedList(User user) throws IllegalArgumentException{
        if(!Tools.isOnList(this, user)) {
            this.getDeniedList().add(user);
        }
        else {
            throw new IllegalArgumentException("user already denied");
        }
        this.setDeniedList(this.getDeniedList());
    }

    public void removeUserFromFriendList(User user) throws IllegalArgumentException{
        if(Tools.onFriendList(this, user)) {
            this.getFriendList().remove(user);
        }
        else {
            throw new IllegalArgumentException("user is not on list.");
        }
        this.setFriendList(this.getFriendList());
    }

    public void removeUserFromDeniedList(User user) throws IllegalArgumentException{
        if(Tools.onDeniedList(this, user)) {
            this.getDeniedList().remove(user);
        }
        else {
            throw new IllegalArgumentException("user is not on list.");
        }
        this.setDeniedList(this.getDeniedList());
    }
    //g&s

    /**
     * getter for user's nickname
     * @return user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * setter for a user's nickname
     * @param nickname new user nickname
     * @throws IllegalArgumentException returns an exception if the new nickname is too short, too long
     * or generally invalid
     */
    public void setNickname(String nickname) throws IllegalArgumentException{

    if (Tools.checkValidString(nickname, 45)) {
            this.nickname = nickname;
        }
    }

    /**
     * getter for user's email
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for a user's email
     * @param email user's new email
     * @throws IllegalArgumentException returns exception if input is too long, too short or invalid
     */
    public void setEmail(String email) throws IllegalArgumentException{
        if (!patternMatches(email)) {
            throw new IllegalArgumentException("email must be valid.");
        }
        if (Tools.checkValidEmailString(email, 45)) {
            this.email = email;
        }
    }

    /**
     * getter for user's first name
     * @return user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    //TODO limit the different characters (1,%,^ etc) allowed to be in a first/last name
    /**
     * setter for a user's first name
     * @param firstName a user's new first name
     * @throws IllegalArgumentException if new name is too long, too short, or generally invalid
     */
    public void setFirstName(String firstName) throws IllegalArgumentException{

        try {
            Tools.checkValidString(firstName, 45);
            Tools.checkDigits(firstName);
            this.firstName = firstName;
        }
        catch (IllegalArgumentException e) {
            switch (e.getMessage()) {
                case "input must be of valid length.":
                    System.out.println("valid length");
                    break;
                case "input must not contain any blank spaces.":
                    System.out.println("blank spaces");
                    break;
                case "input must not contain special characters":
                    System.out.println("special characters");
                    break;
                case "input must not contain digits":
                    System.out.println("digits");
                    break;
                default:
                    System.out.println("unknown error, run!!!");
            }
        }

    }

    /**
     * getter for user's last name
     * @return user's last name
     */
    public String getLastName() {
        return lastName;
    }

    //TODO clean all unnecessary exceptions
    /**
     * setter for a user's last name
     * @param lastName user's new last name(congrats on the wedding/divorce i guess)
     * @throws IllegalArgumentException if new name is too long, too short, or generally invalid
     */
    public void setLastName(String lastName){

        try {
            Tools.checkValidString(lastName, 45);
            Tools.checkDigits(lastName);
            this.lastName = lastName;
        }
        catch (IllegalArgumentException e) {
           switch (e.getMessage()) {
               case "input must be of valid length.":
                   System.out.println("valid length");
                   break;
               case "input must not contain any blank spaces.":
                   System.out.println("blank spaces");
                   break;
               case "input must not contain special characters":
                   System.out.println("special characters");
                   break;
               case "input must not contain digits":
                   System.out.println("digits");
                   break;
               default:
                   System.out.println("unknown error, run!!!");
           }
        }

    }
    /**
     * getter for a user's friend list
     * @return a user's friend list
     */
    public List<User> getFriendList(){
        return friendList;
    }

    /**
     * setter for a user's friend list
     * @param friendList list of users the user's chosen as friends
     */
    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

    public List<User> getDeniedList() {
        return deniedList;
    }

    public void setDeniedList(List<User> deniedList) {
        this.deniedList = deniedList;
    }
}
