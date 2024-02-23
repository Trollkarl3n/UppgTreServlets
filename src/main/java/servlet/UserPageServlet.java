package servlet;

import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet("/userPage")
public class UserPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");

        if (userBean == null || userBean.getStateType() != STATE_TYPE.confirmed) {
            req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
            return;
        }

        if (userBean.getUserType() == USER_TYPE.student) {
            // Fetch courses for student
            LinkedList<String[]> courses = MySQLConnector.getConnector().selectQuery("allCoursesForStudentID", userBean.getId());
            courses.removeFirst();
            req.setAttribute("courses", courses);

            LinkedList<String[]> courseIdList = MySQLConnector.getConnector().selectQuery("justCourseIDForStudentID", userBean.getId());
            courseIdList.removeFirst();

            ArrayList<String> integerCourseIdList = new ArrayList<>();
            for(String[] a : courseIdList)
                integerCourseIdList.addAll(Arrays.asList(a));

            String[] column = new String[5];
            column[0] = "name";
            column[1] = "fName";
            column[2] = "lName";
            column[3] = "fName";
            column[4] = "lName";

            LinkedList<String[]> students = new LinkedList<>();
            for (String i : integerCourseIdList)
                students.addAll(MySQLConnector.getConnector().selectQuery("allPeopleInCourse", i));
            students.removeIf(n -> (Arrays.equals(n, column)));
            req.setAttribute("students", students);

            req.getRequestDispatcher("JSP/userPage.jsp").forward(req, resp);
        } else if (userBean.getUserType() == USER_TYPE.teacher) {
            // Fetch all courses
            LinkedList<String[]> courses = MySQLConnector.getConnector().selectQuery("allFromCourses");
            req.setAttribute("courses", courses);

            // Fetch all students
            LinkedList<String[]> students = MySQLConnector.getConnector().selectQuery("allFromStudents");
            req.setAttribute("students", students);

            req.getRequestDispatcher("JSP/userPage.jsp").forward(req, resp);
        }
    }
}