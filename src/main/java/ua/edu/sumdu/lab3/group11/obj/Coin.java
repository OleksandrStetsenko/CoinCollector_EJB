package ua.edu.sumdu.lab3.group11.obj;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

/**
 * The Class Coin.
 */

@Entity
@Table(name = "coins")
@NamedQuery(name = "Coin.getAll", query = "SELECT c from Coin c")
public class Coin {

    /** The log. */
    static Logger log = Logger.getLogger(Coin.class.getName());

    /** The coin id. */
    @SequenceGenerator(name="coin_seq_generetor", sequenceName="coin_seq", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="coin_seq_generetor")
    @Column(name = "coin_id", length = 20, nullable = false)
    private int coinID;
    
    /** The year. */
    @Column(name = "year", length = 20)
    private int year;		//2014
    
    /** The name. */
    @Column(name = "name", length = 50)
    private String name;	//kopiyka
    
    /** The metall. */
    @Column(name = "metall", length = 50)
    private String metall;	//nikel,steel
    
    /** The diameter_mm. */
    @Column(name = "diameter_mm", length = 20)
    private int diameter_mm;//30
    
    /** The value. */
    @Column(name = "value", length = 20)
    private int value;		//5
    
    /** The weight. */
    @Column(name = "weight", length = 20)
    private int weight;		//503
    
    /** The fullname. */
    @Column(name = "fullname", length = 50)
    private String fullname;	//5 kopiyok

    @JoinColumn(name = "country")
    @ManyToOne()
    private Country country;
    
    /**
     * Instantiates a new coin.
     *
     * @param coinID the coin id
     * @param country the country
     * @param year the year
     * @param name the name
     * @param metall the metall
     * @param diameter_mm the diameter_mm
     * @param value the value
     * @param weight the weight
     * @param fullname the fullname
     */
    
    public Coin(int coinID, Country country,int year, String name, String metall, int diameter_mm, int value, int weight, String fullname) {
        this.coinID = coinID;
        this.country = country;
        this.year = year;
        this.name = name;
        this.metall = metall;
        this.diameter_mm = diameter_mm;
        this.value = value;
        this.fullname = fullname;
        this.weight = weight;

        log.debug("New coin has been created: " + this.toString());
    }
    
    /**
     * Instantiates a new coin.
     */
    public Coin(){}

    /**
     * Gets the coin id.
     *
     * @return the coin id
     */
    public int getCoinID() {
        return coinID;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }
    
    /**
     * Gets the year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the metall.
     *
     * @return the metall
     */
    public String getMetall() {
        return metall;
    }
    
    /**
     * Gets the diameter_mm.
     *
     * @return the diameter_mm
     */
    public int getDiameter_mm() {
        return diameter_mm;
    }
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Gets the weight.
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }
    
    /**
     * Gets the fullname.
     *
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }
    
    // "Set" part
    /**
     * Sets the coin id.
     *
     * @param coinID the new coin id
     */
    public void setCoinID(int coinID) {
        this.coinID = coinID;
    }
    
    /**
     * Sets the country.
     *
     * @param country the new country
     */
    public void setCountry(Country country) {
        this.country = country;
    }
    
    /**
     * Sets the year.
     *
     * @param year the new year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the metall.
     *
     * @param metall the new metall
     */
    public void setMetall(String metall) {
    	this.metall = metall;
    }

    /**
     * Sets the diameter_mm.
     *
     * @param diameter_mm the new diameter_mm
     */
    public void setDiameter_mm(int diameter_mm) {
    	this.diameter_mm = diameter_mm;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(int value) {
    	this.value = value;
    }
    
    /**
     * Sets the weight.
     *
     * @param weight the new weight
     */
    public void setWeight(int weight) {
    	this.weight= weight;
    }
    
    /**
     * Sets the fullname.
     *
     * @param fullname the new fullname
     */
    public void setFullname(String fullname) {
    	this.fullname= fullname;
    }

    /* (non-Javadoc)
             * @see java.lang.Object#equals(java.lang.Object)
             */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Coin coin = (Coin) o;

        if (coinID != coin.coinID)
            return false;
        
        if (year != coin.year)
            return false;
        if (diameter_mm != coin.diameter_mm)
            return false;
        if (value != coin.value)
            return false;
        if (weight != coin.weight)
            return false;
        /*if (country != coin.country)
            return false;*/

        if (name != null ? !name.equals(coin.name) : coin.name != null)
            return false;
        if (metall != null ? !metall.equals(coin.metall) : coin.metall != null)
            return false;
        if (fullname != null ? !fullname.equals(coin.fullname) : coin.fullname != null)
            return false;

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return coinID+year+name.hashCode()+metall.hashCode()+diameter_mm+value+weight+fullname.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Coin{" +
                "coinID=" + coinID +
                ", fullname=" + fullname + 
                ", name=" + name + 
                ", country ="+ country.getCountryName()+
		        ", year ="+ year+
		        ", metall ="+ metall+
		        ", diameter_mm ="+ diameter_mm+
		        ", value ="+ value+
		        ", weight ="+ weight+
		        '}';
    }


}
