package ua.edu.sumdu.lab3.group11.obj;

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name = "user_coins")
@NamedQueries({
        @NamedQuery(name = "UserCoin.coinExist", query = "SELECT uc FROM UserCoin uc WHERE uc.userID = :userID AND uc.coinID = :coinID"),
        @NamedQuery(name = "UserCoin.DeleteCoin", query = "DELETE FROM UserCoin uc WHERE uc.userID = :userID AND uc.coinID = :coinID"),
        @NamedQuery(name = "UserCoin.userCoins", query = "SELECT uc FROM UserCoin uc WHERE uc.userID = :userID")})
public class UserCoin {

    private static Logger log = Logger.getLogger(UserCoin.class.getName());

    @Id
    @Column(name = "user_id", length = 20, nullable = false)
    private int userID;

    @Id
    @Column(name = "coin_id", length = 20, nullable = false)
    private int coinID;

    public UserCoin(int userID, int coinID) {
        this.userID = userID;
        this.coinID = coinID;
    }

    public UserCoin() {
    }

    public int getUserID() {
        return userID;
    }

    public int getCoinID() {
        return coinID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setCoinID(int coinID) {
        this.coinID = coinID;
    }
}
