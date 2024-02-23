package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("userBean") != null && req.getParameter("logout") != null){
            System.out.println("Loging out user: " + req.getSession().getAttribute("userBean"));
            req.getSession().setAttribute("userBean", null);
            req.getSession().setMaxInactiveInterval(0);

        }
        req.getRequestDispatcher("JSP/index.jsp").forward(req, resp);
    }
}