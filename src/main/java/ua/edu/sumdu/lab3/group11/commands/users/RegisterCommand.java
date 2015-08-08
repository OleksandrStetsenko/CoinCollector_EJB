package ua.edu.sumdu.lab3.group11.commands.users;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.commands.FrontCommand;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;
import ua.edu.sumdu.lab3.group11.dao.users.UserDao;
import ua.edu.sumdu.lab3.group11.dao.users.UserService;
import ua.edu.sumdu.lab3.group11.obj.User;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterCommand extends FrontCommand {

    static Logger log = Logger.getLogger(RegisterCommand.class.getName());

    @EJB
    private UserService userService;

    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        try {
            userService = (UserService) new InitialContext().lookup("java:app/coincollector/userService");
        } catch (NamingException e) {
            request.setAttribute("msg", "Can not inject userService");
            request.setAttribute("ex", e);
            forward("controller?action=error");

            return;
        }

        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String errorMsg = null;

        if(username == null || username.equals("")) {
            errorMsg = "Username can't be null or empty.";
        }
        if(password == null || password.equals("")) {
            errorMsg = "Password can't be null or empty.";
        }

        if(errorMsg != null){

            log.error(errorMsg);
            request.setAttribute("message", errorMsg);
            forward("/register.jsp");

        }else{

            //add new user to DB
            if (userService.read(username) != null) {
                log.error("The user with this login already exists.");
                request.setAttribute("message", "The user with this login already exists.");
                forward("/register.jsp");
            } else {
                //User user = new User(5, username, password, false);
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userService.create(user);

                //forward to login page to login
                request.setAttribute("message", "Registration successful, please login below.");
                forward("/login.jsp");
            }

        }

    }

}
