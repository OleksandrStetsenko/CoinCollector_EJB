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
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class DeleteCoinCommand.
 */
public class DeleteCoinCommand extends FrontCommand {


    /** The log. */
    private static Logger log = Logger.getLogger(DeleteCoinCommand.class.getName());
    
    /** The Constant SAC_PAGE. */
    private static final String SAC_PAGE = "controller?action=showAllCoins";

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.commands.FrontCommand#process()
     */
    @EJB
    private CoinService coinService;

    
    @Override
    public void process() throws ServletException, IOException, DBRecordException {
    	
    	try {
            coinService = (CoinService) new InitialContext().lookup("java:app/coincollector/coinService");
        } catch (NamingException e) {
            throw new ServletException("Can not inject coinService");
        }
    	
    	// only administrator can delete- check
    	User currentUser = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
         if (currentUser == null || (! currentUser.isAdmin())) {
             //redirect to notAvalible.jsp
             forward(Settings.NO_AVALIBLE);
             return;
         }
    	                
            int coinID = Integer.parseInt(request.getParameter("coinID"));
			coinService.delete(coinID);

            forward(SAC_PAGE);
        
        
    }

}
