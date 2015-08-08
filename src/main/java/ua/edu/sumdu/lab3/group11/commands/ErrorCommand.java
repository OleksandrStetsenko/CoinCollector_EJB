package ua.edu.sumdu.lab3.group11.commands;

import org.apache.log4j.Logger;
import ua.edu.sumdu.lab3.group11.dao.DBRecordException;

import javax.servlet.ServletException;
import java.io.IOException;

public class ErrorCommand extends FrontCommand {

    private static Logger log = Logger.getLogger(ErrorCommand.class.getName());


    @Override
    public void process() throws ServletException, IOException, DBRecordException {

        String message = (String) request.getAttribute("msg");
        Throwable ex = (Throwable) request.getAttribute("ex");

        request.setAttribute("msg", message);
        request.setAttribute("stack", ex.getMessage());

        log.error(message, ex);

        forward("/error.jsp");

    }
}
