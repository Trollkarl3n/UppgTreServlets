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
            LinkedList<String[]> courses = fetchStudentCourses(userBean.getId());
            req.setAttribute("courses", courses);

            // Fetch students in the same courses
            LinkedList<String[]> students = fetchStudentsInCourses(userBean.getId());
            req.setAttribute("students", students);
        } else if (userBean.getUserType() == USER_TYPE.teacher) {
            // Fetch all courses
            LinkedList<String[]> courses = MySQLConnector.getConnector().selectQuery("allFromCourses");
            req.setAttribute("courses", courses);

            // Fetch all students
            LinkedList<String[]> students = MySQLConnector.getConnector().selectQuery("allFromStudents");
            req.setAttribute("students", students);
        }

        req.getRequestDispatcher("JSP/userPage.jsp").forward(req, resp);
    }

    private LinkedList<String[]> fetchStudentCourses(String studentId) {
        LinkedList<String[]> courses = MySQLConnector.getConnector().selectQuery("allCoursesForStudentID", studentId);
        courses.removeFirst(); // Remove header row
        return courses;
    }

    private LinkedList<String[]> fetchStudentsInCourses(String studentId) {
        LinkedList<String[]> courseIdList = MySQLConnector.getConnector().selectQuery("justCourseIDForStudentID", studentId);
        courseIdList.removeFirst(); // Remove header row

        ArrayList<String> integerCourseIdList = new ArrayList<>();
        for (String[] courseIdArray : courseIdList) {
            integerCourseIdList.addAll(Arrays.asList(courseIdArray));
        }

        LinkedList<String[]> students = new LinkedList<>();
        for (String courseId : integerCourseIdList) {
            LinkedList<String[]> studentsInCourse = MySQLConnector.getConnector().selectQuery("allPeopleInCourse", courseId);
            studentsInCourse.removeFirst(); // Remove header row
            String[] column = {"name", "fName", "lName", "fName", "lName"}; // Assuming this represents a header row
            studentsInCourse.removeIf(row -> Arrays.equals(row, column)); // Remove header if present
            students.addAll(studentsInCourse);
        }

        return students;
    }
}
