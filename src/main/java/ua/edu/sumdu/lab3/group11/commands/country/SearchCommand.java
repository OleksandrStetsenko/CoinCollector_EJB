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
public class SearchCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(SearchCommand.class.getName());
    private static final String COINS_PAGE = "allcoins.jsp";
    @EJB
    private CoinService coinService;
    @EJB
    private CountryService countryService;
    @EJB
    private UserCoinService userCoinService;
    @EJB
    private UserService userService;

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        User u = (User) request.getSession().getAttribute("currentUser");
        int userID = u.getUserID();

        List<Coin> list = new ArrayList<Coin>();

        String countryStr = request.getParameter("country");
        String yearStr = request.getParameter("year");
        String metall = request.getParameter("metall");
        log.info("User selected country = " + countryStr + " year = " + yearStr
                + " metall = " + metall);

        int countryID = 0;
        int year = 0;

        if (!countryStr.equals("allCountries")) {
            countryID = Integer.parseInt(countryStr);
        }
        if (!yearStr.equals("allYears")) {
            year = Integer.parseInt(yearStr);
        }
        if (metall.equals("allMetalls")) {
            metall = "";
        }

        try {
            countryService = (CountryService) new InitialContext()
                    .lookup("java:app/coincollector/countryService");
            log.info(" Initial context from countryService");
            coinService = (CoinService) new InitialContext()
                    .lookup("java:app/coincollector/coinService");
            log.info(" Initial context from coinService");
            userCoinService = (UserCoinService) new InitialContext()
                    .lookup("java:app/coincollector/userCoinService");
            userService = (UserService) new InitialContext()
                    .lookup("java:app/coincollector/userService");
        } catch (NamingException e) {
            log.error("Can not inject userCoinService", e);
            return;
        }

        String requestFromPage = (String) request.getSession().getAttribute("page");
        log.info(" Var requestFromPage =" + requestFromPage);

        if (countryID == 0 && year == 0 && metall.equals("")) {

            switch (requestFromPage) {
                case "allCoins":
                    list = coinService.getAll();
                    log.info(" Search from page =" + requestFromPage);
                    break;
                case "myCoins":

                    list = userCoinService.getUserCoins(u);
                    log.info(" Search from page =" + requestFromPage);
                    break;
            }
        } else {
            Country country = null;//new Country();

            if (countryID != 0) {
                country = countryService.getCountryByPK(countryID);
            } else {
                country = new Country();
                country.setCountryName("NoCountry");
            }
            switch (requestFromPage) {
                case "allCoins":
                    list = coinService.getCoinsOfSearch(country, year, metall);
                    log.info(" Search with  parametrs from page :"
                            + requestFromPage);
                    break;
                case "myCoins":
                    list = userService.getUserCoinsOfSearch(u, country, year, metall);
                    log.info(" Search with  parametrs from page :"
                            + requestFromPage);
                    break;
            }
        }
        request.setAttribute("list", list);
        forward(COINS_PAGE);
        log.info(" Forward from allcoins.jsp ");
    }

}
