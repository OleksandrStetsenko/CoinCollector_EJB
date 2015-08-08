package ua.edu.sumdu.lab3.group11.dao.counties;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Yulia Lukianenko on 09.01.2015.
 */
@Stateless(name = "countryService")
public class CountryService {

    private static Logger log = Logger.getLogger(CountryService.class);

    @PersistenceContext(unitName = "COLLECTOR")
    public EntityManager em;

    public List<Country> getAll(){
        log.info(" Countries -> getAllCountry. ");
        TypedQuery<Country> namedQuery = em.createNamedQuery("Country.getAll",Country.class);
        return namedQuery.getResultList();
    }

    public Country getCountryByPK(int id){
        return em.find(Country.class, id);
    }

    public List<Coin> getCoinOfCountry (int id){
        return em.find(Country.class, id).getCoins();
    }

}
