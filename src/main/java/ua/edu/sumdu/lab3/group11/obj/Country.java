package ua.edu.sumdu.lab3.group11.obj;

import javax.persistence.*;

import org.apache.log4j.Logger;

import java.util.List;
import javax.persistence.FetchType;

/**
 *
 * @author Yulia Lukianenko
 */
@Entity
@Table(name = "countries")
@NamedQueries({
        @NamedQuery(name = "Country.getAll", query = "SELECT coun from Country coun")})
       // @NamedQuery(name = "Country.findByPrimaryKey", query = "SELECT coun FROM Country coun WHERE coun.countryId = :id")})
public class Country {
    
    private final static Logger log = Logger.getLogger(Country.class);

    @Id
    @Column(name = "country_id", length = 20, nullable = false)
    private int countryId;

    @Column(name = "name", length = 50)
    private String countryName;

   @OneToMany(targetEntity = Coin.class,
            mappedBy = "country",
            fetch = FetchType.LAZY)
    private List<Coin> coins;

    public List<Coin> getCoins() {
        return coins;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
             builder.append("Country \"")
                    .append(getCountryName())
                    .append("\" with country_ID = ")
                    .append(getCountryId());
        return builder.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
    
        if (obj == this)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Country country = (Country) obj;
        return countryId == country.countryId &&
                countryName.equals(country.countryName) ;
    }
    
    @Override
    public int hashCode(){
        return 3 * countryId + 7 * countryName.hashCode();
    }
}
