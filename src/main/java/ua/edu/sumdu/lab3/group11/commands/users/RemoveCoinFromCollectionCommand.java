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
import java.util.List;

public class RemoveCoinFromCollectionCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(RemoveCoinFromCollectionCommand.class.getName());

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

        //add coin to user collection
        int coinID = Integer.parseInt(request.getParameter("coinID"));
        log.debug("user " + user + " coinID " + coinID);

        userCoinService.delete(new UserCoin(user.getUserID(), coinID));

        //get user collection
        request.setAttribute("list", userCoinService.getUserCoins(user));

        //show user coin collection
        forward("/allcoins.jsp");

    }
}
