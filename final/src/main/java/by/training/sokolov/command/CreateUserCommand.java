package by.training.sokolov.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        return "create";
    }

}