package ua.edu.sumdu.lab3.group11.commands.country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinService;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryService;
import ua.edu.sumdu.lab3.group11.dao.users.UserCoinService;
import ua.edu.sumdu.lab3.group11.dao.users.UserService;
import ua.edu.sumdu.lab3.group11.obj.Coin;
import ua.edu.sumdu.lab3.group11.obj.Country;
import ua.edu.sumdu.lab3.group11.obj.User;

/**
 *
 * @author Yulia Lukianenko
 */
public class CoinListCommand extends FrontCommand {

    private static final Logger log = Logger.getLogger(CoinListCommand.class.getName());
    private static final String COIN_PAGE = "allcoins.jsp";
    @EJB
    private CountryService countryService;
    @EJB
    private UserService userService;
    @EJB
    private UserCoinService userCoinService;
    @EJB
    private CoinService coinService;

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User u = (User) request.getSession().getAttribute("currentUser");
        int userID = u.getUserID();

        List<Coin> list = new ArrayList<Coin>();
        String requestFromPage = (String) request.getSession().getAttribute("page");

        try {
            countryService = (CountryService) new InitialContext()
                    .lookup("java:app/coincollector/countryService");
            log.info(" Initial context from countryService");
            coinService = (CoinService) new InitialContext()
                    .lookup("java:app/coincollector/coinService");
            log.info(" Initial context from coinService");
            userService = (UserService) new InitialContext()
                    .lookup("java:app/coincollector/userService");
            log.info(" Initial context from userCoinService");
            userCoinService = (UserCoinService) new InitialContext()
                    .lookup("java:app/coincollector/userCoinService");
        } catch (NamingException e) {
            log.error("Can not inject userCoinService", e);
            return;
        }

        if (request.getParameter("countryID").equals("0")) {
            switch (requestFromPage) {
                case "allCoins":
                    list = coinService.getAll();
                    log.info(" ' clic on all countries ' from page ="
                            + requestFromPage);
                    break;
                case "myCoins":
                    list = userCoinService.getUserCoins(u);
                    log.info(" ' clic on all countries ' from page ="
                            + requestFromPage);
                    break;
            }
        } else {
            int countryID = Integer.parseInt(request.getParameter("countryID"));
            log.info(" countryID = " + countryID);
            switch (requestFromPage) {
                case "allCoins":
                    list = countryService.getCoinOfCountry(countryID);
                    log.info(" ' clic on countries ' with ID = " + countryID
                            + " in page =" + requestFromPage);
                    break;
                case "myCoins":
                    Country country = countryService.getCountryByPK(countryID);
                    list = userService.getUserCoinsOfCountry(u, country);
                    log.info(" ' clic on countries ' with ID = " + countryID
                            + " in page =" + requestFromPage);
                    break;
            }

        }
        log.info("List of Coins : " + list.toString());
        request.setAttribute("list", list);
        forward(COIN_PAGE);
        log.info(" Forward CoinList to allcoins.jsp ");
    }

}
