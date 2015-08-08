package ua.edu.sumdu.lab3.group11.commands.country;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.coins.CoinService;
import ua.edu.sumdu.lab3.group11.dao.counties.CountryService;
import ua.edu.sumdu.lab3.group11.obj.Country;

/**
 *
 * @author Yulia Lukianenko
 */
public class InitDataForSearch {

    private static Logger log = Logger.getLogger(InitDataForSearch.class);

    @EJB
    CountryService countryService;

    @EJB
    CoinService coinService;

    /**
     * Initialization of parameters of search for drop-down lists
     */

    public void initDataSearch(HttpServletRequest request)
            throws ServletException, IOException, DBRecordException {

        log.info("Init dataSearch ");

        try {
            countryService = (CountryService) new InitialContext()
                    .lookup("java:app/coincollector/countryService");
            log.info(" Initial context from countryService");
            coinService = (CoinService) new InitialContext()
                    .lookup("java:app/coincollector/coinService");
            log.info(" Initial context from coinService");
        } catch (NamingException e) {
            log.error("Can not inject userCoinService", e);
            return;
        }

        List<Country> list = countryService.getAll();
        log.info("list of Countries : " + list.toString());
        request.getSession().setAttribute("listCountrys", list);

        List<String> listYears = coinService.getYears();
        log.info("list of Years : " + listYears.toString());
        request.getSession().setAttribute("listYears", listYears);

        List<String> listMetalls = coinService.getMetalls();
        log.info("list of Mettals : " + listMetalls.toString());
        request.getSession().setAttribute("listMetalls", listMetalls);

    }

    /**
     * Initialization of parameters from tree countries
     *
     */
    public void initDataCountryList(HttpServletRequest request)
            throws ServletException, IOException, DBRecordException {
        log.info("Init data CountryList ");

        try {
            countryService = (CountryService) new InitialContext()
                    .lookup("java:app/coincollector/countryService");
        } catch (NamingException e) {
            log.error("Can not inject userCoinService", e);
            return;
        }
        if (request.getSession().getAttribute("listCountries") == null) {

            List<Country> list = countryService.getAll();
            log.info(" Get list of Countries length = " + list.size());
            request.getSession().setAttribute("listCountries", list);
            log.info(" List Country : " + list.toString());
        }
    }

}
