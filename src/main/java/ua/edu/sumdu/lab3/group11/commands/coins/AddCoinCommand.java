/**
 *
 * @author Vitalii Tregub
 */
package ua.edu.sumdu.lab3.group11.commands.coins;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.Settings;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinService;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryService;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class AddCoinCommand.
 */
public class AddCoinCommand extends FrontCommand {


    /** The log. */
    private static Logger log = Logger.getLogger( AddCoinCommand.class.getName());
    
    /** The Constant COIN_PAGE. */
    private static final String COIN_PAGE = "controller?action=showAllCoins";
    
    /** The Constant PREP_COIN_PAGE. */
    private static final String PREP_COIN_PAGE = "controller?action=prepCoin";
    
    /** The error msg. */
    String errorMsg;
   
    
    /**
     * Gets the text user input.
     *
     * @param name the name
     * @return the text user input
     */
    private String getTextUserInput(String name){
    	if (request.getParameter(name)!=null && !request.getParameter(name).matches("(\\<(/?[^\\>]+)\\>)")){ 
    		log.debug(name+" recognize Ok");
    		return request.getParameter(name);
    	}else {
    		log.debug(name+" recognize FAILED");
    		errorMsg=errorMsg+ " Do not use html tag in \""+name+"\" <br/>";
    		return "";
    	}
    	
    }
    
    /**
     * Gets the num user input.
     *
     * @param name the name
     * @return the num user input
     */
    private int getNumUserInput(String name){
    	if (request.getParameter(name)!=null && request.getParameter(name).matches("[0-9]+")){ 
    		log.debug(name+" recognize as digit Ok");
    		return Integer.parseInt(request.getParameter(name));
    	}else {
    		log.debug(name+" recognize as digit FAILED");
    		errorMsg=errorMsg+ " Only digits alowed in \""+name+"\" <br/>";
    		return 0;
    	}
    	
    }
    
    
    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.commands.FrontCommand#process()
     */
    @EJB
    private CoinService coinService;
	@EJB
	private CountryService countryService;

    @Override
    public void process() throws ServletException, IOException, DBRecordException {
    	
    	try {
            coinService = (CoinService) new InitialContext().lookup("java:app/coincollector/coinService");
			countryService = (CountryService) new InitialContext().lookup("java:app/coincollector/countryService");
        } catch (NamingException e) {
            throw new ServletException("Can not inject coinService");
        }
    	
    	 User currentUser = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
         if (currentUser == null || (! currentUser.isAdmin())) {
             //redirect to notAvalible.jsp
             forward(Settings.NO_AVALIBLE);
             return;
         }
    	
    	errorMsg = ""; //clear Error message
    	
    	String fullname = getTextUserInput("fullname");
    	String name = getTextUserInput("name");
    	String metall = getTextUserInput("metall");

    	int country = getNumUserInput("country");//no need for check?- user pick it from the list
    	int year =  getNumUserInput("year");
    	int diameter_mm =  getNumUserInput("diameter_mm");
    	int value =  getNumUserInput("value");
    	int weight = getNumUserInput("weight");
    	
    	 
    	int coinID = 0;
    	
    	if (!request.getParameter("coinID").equals("")){	//if exist, then edit coin, not create
    		log.debug("trying to define coinID...");
    		//coinID = Integer.parseInt(request.getParameter("coinID"));
    		coinID =  getNumUserInput("coinID");
    		log.debug("coinID DEFINED="+coinID+" so, update coin");
    	}    	
    	/**/
    	
        if (!errorMsg.equals("")) { // check, if no errors (is user input correct data on page)
            log.error(errorMsg);
            request.setAttribute("message", errorMsg);
            forward(PREP_COIN_PAGE);
        } else {  //if user input correct:
			Country countryOb = countryService.getCountryByPK(country);
        	Coin coin = new Coin(coinID, countryOb,year,name, metall,diameter_mm,value,weight,fullname);

		            // add new coin
		            if (coinID==0){
		            	log.debug("AddCoinCommand- Create new coin");
		            	coinService.create(coin);	
		            }else{
		            	log.debug("AddCoinCommand- update coin:"+coinID);
		            	coinService.update(coin);
		            }
		       
		            forward(COIN_PAGE);
        	}

	
    }
}
