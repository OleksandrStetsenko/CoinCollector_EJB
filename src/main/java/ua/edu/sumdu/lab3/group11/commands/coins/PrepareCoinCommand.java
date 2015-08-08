/**
 *
 * @author Vitalii Tregub
 */
package ua.edu.sumdu.lab3.group11.commands.coins;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.Settings;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.commands.users.EditUserCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinService;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryDao;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryService;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;


public class PrepareCoinCommand extends FrontCommand {


    private static Logger log = Logger.getLogger(PrepareCoinCommand.class.getName());
    private static final String ADDCOIN_PAGE = "addcoin.jsp";

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
         
         //just for testing, while "country" is old 
         List<Country> list = countryService.getAll();
         /*Country country = new Country();
         country.setCountryId(1);
         country.setCountryName("Ukraine");
         list.add(country);*/
         request.setAttribute("list", list);
         
         
         /*Check, edit or create new*/    
             
             String coinID = request.getParameter("coinID"); // if exist- this is redirect from allcoins button "edit" 
             
             log.debug("coinID ="+coinID);
             if (coinID!=null&&coinID.matches("[0-9]+")){
            	 request.setAttribute("head","Update coin"); //to edit exist coin
            	 
            	 Coin coin = coinService.read(Integer.parseInt(coinID));
                 request.setAttribute("coin", coin);
            	 
             }else{
            	 request.setAttribute("head","Create new coin");	//to add new coin
             }
             log.debug("forward to "+ADDCOIN_PAGE);
             forward(ADDCOIN_PAGE);

    }
}
