/**
 *
 * @author Vitalii Tregub
 */

package ua.edu.sumdu.lab3.group11.commands.coins;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.commands.country.InitDataForSearch;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinDao;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinService;
import ua.edu.sumdu.lab3.group11.dao.users.UserService;
import ua.edu.sumdu.lab3.group11.obj.Coin;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowAllCoinsCommand.
 */
public class ShowAllCoinsCommand extends FrontCommand {

    /** The log. */
    private static Logger log = Logger.getLogger(ShowAllCoinsCommand.class.getName());
    
    /** The Constant SAC_PAGE. */
    private static final String SAC_PAGE = "allcoins.jsp";

    /* (non-Javadoc)
     * @see ua.edu.sumdu.lab3.group11.commands.FrontCommand#process()
     */
    @EJB
    private CoinService coinService;

    
    @Override
    public void process() throws ServletException, IOException, DBRecordException {
        InitDataForSearch initData = new InitDataForSearch();
        initData.initDataCountryList(request);
        initData.initDataSearch(request);

    	try {
            coinService = (CoinService) new InitialContext().lookup("java:app/coincollector/coinService");
        } catch (NamingException e) {
            throw new ServletException("Can not inject coinService");
        }

        String page = "allCoins";
        log.info(" Var page ="+page);
        request.getSession().setAttribute("page",page);

    	List<Coin> list=coinService.getAll();
    	request.setAttribute("list", list);

        forward(SAC_PAGE);
    	
    }

   
}
