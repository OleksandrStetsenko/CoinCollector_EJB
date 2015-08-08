package ua.edu.sumdu.lab3.group11.commands.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.users.UserCoinService;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.dao.users.UserService;
import ua.edu.sumdu.lab3.group11.obj.User;
import ua.edu.sumdu.lab3.group11.obj.UserCoin;
import ua.edu.sumdu.lab3.group11.servlets.AuthenticationFilter;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class AddCoinToCollectionCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(AddCoinToCollectionCommand.class.getName());

    @EJB
    private UserCoinService userCoinService;

    @EJB
    private UserService userService;

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        try {
            userCoinService = (UserCoinService) new InitialContext().lookup("java:app/coincollector/userCoinService");
            userService = (UserService) new InitialContext().lookup("java:app/coincollector/userService");
        } catch (NamingException e) {
            request.setAttribute("msg", "Can not inject userCoinService");
            request.setAttribute("ex", e);
            forward("controller?action=error");

            return;
        }

        User user = (User) request.getSession().getAttribute(AuthenticationFilter.CURRENT_USER);
        log.debug("current user: " + user);

        if(request.getParameterValues("selectedItems") == null) {
            request.setAttribute("message", "First select a coin.");
            forward("controller?action=showAllCoins");
            return;
        }
        String[] selectedItems = request.getParameterValues("selectedItems");

        boolean someCoinsExist = false;
        for (String selectedItem : selectedItems) {
            int coinID = Integer.parseInt(selectedItem);

            UserCoin userCoin = new UserCoin(user.getUserID(), coinID);

            if ( ! userCoinService.isCoinExist(userCoin) ) {
                //add coin to user collection
                userCoinService.create(userCoin);
            } else {
                someCoinsExist = true;
            }
        }

        if (someCoinsExist) {
            request.setAttribute("message", "Some coins already exists in your collection");
        }

        forward("controller?action=showUserCoins");

    }
}
