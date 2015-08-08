package ua.edu.sumdu.lab3.group11.dao.coins;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.dao.counties.CountryService;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;

@Stateless(name = "coinService")
public class CoinService {

    private static Logger log = Logger.getLogger(CoinService.class.getName());

    @PersistenceContext(unitName = "COLLECTOR")
    public EntityManager em;

    @EJB
    private CountryService countryService;
    
    public Coin create(Coin coin){
        return em.merge(coin);
    }

    public Coin read(int key){
        return em.find(Coin.class, key);
    }
    
    public void update(Coin coin){
        em.merge(coin);
    }
    
    public void delete(int key){
        Coin coin = read(key);
        log.debug("Coin to be deleted: " + coin);
        em.remove(coin);
    }

    public List<Coin> getAll(){
        TypedQuery<Coin> namedQuery = em.createNamedQuery("Coin.getAll", Coin.class);
        return namedQuery.getResultList();
    }

    public List<String> getMetalls() {
        return em.createQuery("select c.metall from Coin c GROUP BY c.metall ", String.class).getResultList();
    }

    public List<String> getYears() {
        return em.createQuery("select c.year from Coin c GROUP BY c.year ", String.class).getResultList();
    }

    public List<Coin> getCoinsOfSearch(Country country, int year, String metall){
        String conditions = selectConditions(country, year, metall);
        TypedQuery<Coin> query = em.createQuery("select c from Coin c "+conditions, Coin.class);
        if (!country.getCountryName().equals("NoCountry")){
            query.setParameter("country", country);
        }
        List<Coin> list = query.getResultList();
        return list;
    }

    private String selectConditions(Country param1, int param2, String param3){
        StringBuilder builder = new StringBuilder();
        builder.append("where");
        if (!param1.getCountryName().equals("NoCountry")) {
            builder.append(" c.country = :country ");
        }
        if (param2 != 0) {
            if(!builder.toString().equals("where")){
                builder.append(" and ");
            }

            builder.append(" c.year = ")
                    .append(param2);

        }
        if (!param3.equals("")) {
            if(!builder.toString().equals("where")) {
                builder.append(" and ");
            }
            builder.append(" c.metall =  ")
                    .append("'")
                    .append(param3)
                    .append("'");
        }
        String conditions = builder.toString();

        log.debug(" Conditions = " + conditions);
        return conditions;
    }

}
