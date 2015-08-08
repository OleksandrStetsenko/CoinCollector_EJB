package ua.edu.sumdu.lab3.group11.dao.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinService;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryService;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.obj.UserCoin;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Stateless(name = "userCoinService")
public class UserCoinService {

    private static Logger log = Logger.getLogger(UserCoinService.class.getName());

    @PersistenceContext(unitName = "COLLECTOR")
    public EntityManager em;

    @EJB
    private CoinService coinService;

    @EJB
    private CountryService countryService;

    public UserCoin create(UserCoin userCoin){
        return em.merge(userCoin);
    }

    public boolean isCoinExist(UserCoin userCoin) {

        boolean exist = false;

        Query queryCoinExist = em.createNamedQuery("UserCoin.coinExist");
        queryCoinExist.setParameter("userID", userCoin.getUserID());
        queryCoinExist.setParameter("coinID", userCoin.getCoinID());
        List<UserCoin> usersCoins = queryCoinExist.getResultList();
        if ( ! usersCoins.isEmpty() ) {
            exist = true;
        }

        return exist;
    }

    public void delete(UserCoin userCoin){
        Query queryDeleteUserCoin = em.createNamedQuery("UserCoin.DeleteCoin");
        queryDeleteUserCoin.setParameter("userID", userCoin.getUserID());
        queryDeleteUserCoin.setParameter("coinID", userCoin.getCoinID());
        queryDeleteUserCoin.executeUpdate();
    }

    public List<Coin> getUserCoins(User user){
	
        return em.createQuery("select c from Coin c join UserCoin uc where uc.coinID = c.coinID AND uc.userID = :user")
                .setParameter("user",user.getUserID())
                .getResultList();

    }
}