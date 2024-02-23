package servlet;

import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("errorMessage", "");
        req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        // Retrieving data from loginForm
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("user_type");

        // Comparing data with DB for student or teacher
        if (userType != null && (userType.equals("student") || userType.equals("teacher"))) {
            LinkedList<String[]> data = MySQLConnector.getConnector().selectQuery(userType + "Login", username, password);

            if (data.size() > 1) {
                req.getSession().setMaxInactiveInterval(360);

                USER_TYPE userTypeEnum = userType.equals("student") ? USER_TYPE.student : USER_TYPE.teacher;
                PRIVILAGE_TYPE privilageTypeEnum = userType.equals("student") ? PRIVILAGE_TYPE.user : resolvePrivilageType(data.get(1)[8]);

                UserBean userBean = new UserBean(data.get(1)[0], userTypeEnum, privilageTypeEnum, STATE_TYPE.confirmed);
                req.getSession().setAttribute("userBean", userBean);
                req.getRequestDispatcher("/userPage").forward(req, resp);
            } else {
                req.getSession().setAttribute("errorMessage", userType + " not found");
                req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
        }
    }

    private PRIVILAGE_TYPE resolvePrivilageType(String privilageType) {
        if (privilageType.equals("user")) {
            return PRIVILAGE_TYPE.user;
        } else if (privilageType.equals("admin")) {
            return PRIVILAGE_TYPE.admin;
        } else {
            return null;
        }
    }
}