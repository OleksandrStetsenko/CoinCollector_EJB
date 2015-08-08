package ua.edu.sumdu.lab3.group11.dao.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.country.CoinListCommand;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless(name = "userService")
public class UserService {

    //public EntityManager em = Persistence.createEntityManagerFactory("COLLECTOR").createEntityManager();

    private static Logger log = Logger.getLogger(UserService.class.getName());

    @PersistenceContext(unitName = "COLLECTOR")
    public EntityManager em;

    public User create(User user){
        return em.merge(user);
    }

    public User read(int key){
        return em.find(User.class, key);
    }

    public User read(String name) {

        User returnedUser = null;

        Query queryUserFindByName = em.createNamedQuery("User.findByUsername");
        queryUserFindByName.setParameter("username", name);
        List<User> users = queryUserFindByName.getResultList();
        if ( ! users.isEmpty() ) {
            returnedUser = users.get(0);
        }

        return returnedUser;
    }

    public void update(User user){
        em.merge(user);
    }

    public void delete(User user) {
        delete(user.getUserID());
    }

    private void delete(int id){
        User user = read(id);
        log.debug("User to be deleted: " + user);
        em.remove(user);
    }

    public List<User> getAll(){
        TypedQuery<User> namedQuery = em.createNamedQuery("User.getAll", User.class);
        return namedQuery.getResultList();
    }

    public List<Coin> getUserCoinsOfCountry(User user, Country country){
        List<Coin> listCoins = em.createQuery("select c from Coin c join UserCoin uc where uc.coinID = c.coinID " +
                "AND uc.userID = :user and c.country = :country")
                .setParameter("country", country)
                .setParameter("user", user.getUserID())
                .getResultList();
        return listCoins;
    }

    public List<Coin> getUserCoinsOfSearch(User user, Country country, int year, String metal){
        String conditions = selectConditions(country, year, metal);
        log.info("Conditions : "+conditions);

        Query query = em.createQuery("select c from Coin c join UserCoin uc where uc.coinID = c.coinID " +
                "AND uc.userID = :user " + conditions);
        query.setParameter("user", user.getUserID());
        if(!country.getCountryName().equals("NoCountry")) {
            query.setParameter("country", country);
        }
        List<Coin> listCoins = query.getResultList();
        log.info(" listCoins after search have a size = "+listCoins.size());
        em.clear();
        return listCoins;
    }

    private String selectConditions(Country param1, int param2, String param3){
        StringBuilder builder = new StringBuilder();

        if (!param1.getCountryName().equals("NoCountry")) {
            builder.append(" and c.country = :country ");
        }
        if (param2 != 0) {
            builder.append(" and c.year = ")
                    .append(param2);
        }
        if (!param3.equals("")) {
            builder.append(" and c.metall =  ")
                    .append("'")
                    .append(param3)
                    .append("'");
        }
        String conditions = builder.toString();

        log.debug(" Conditions = " + conditions);
        return conditions;
    }
}
