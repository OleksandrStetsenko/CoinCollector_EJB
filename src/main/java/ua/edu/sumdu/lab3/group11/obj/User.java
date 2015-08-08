package ua.edu.sumdu.lab3.group11.obj;

import org.apache.log4j.Logger;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "SELECT u from User u"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")})
public class User {

    private static Logger log = Logger.getLogger(User.class.getName());

    @SequenceGenerator(name="user_seq_generetor", sequenceName="user_seq", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq_generetor")
    @Column(name = "user_id", length = 20, nullable = false)
    private int userID;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "admin")
    private boolean admin;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="user_coins",
            joinColumns={
            @JoinColumn(name="user_id", referencedColumnName = "user_id")},
            inverseJoinColumns={
            @JoinColumn(name="coin_id", referencedColumnName = "coin_id")}
    )
    private List<Coin> coins;

    /**
     * Creates new user
     * @param userID
     * @param username
     * @param password
     * @param admin
     */
    public User(int userID, String username, String password, boolean admin) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.admin = admin;

        log.debug("New user has been created: " + this.toString());
    }

    public User() {}


    /**
     * Returns user ID
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns user password
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns true if user is admin
     * @return is admin
     */
    public boolean isAdmin() {
        return admin;
    }


    /**
     * Sets user ID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets user password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets admin rights
     * @param admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (admin != user.admin)
            return false;

        if (userID != user.userID)
            return false;

        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;

        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }

}
