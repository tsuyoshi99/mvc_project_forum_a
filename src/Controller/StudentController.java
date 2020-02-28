package Controller;

import Model.Student;
import Model.StudentModel;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentController extends HttpServlet {
    Student student = null;
    StudentModel studentModel = null;

    boolean result;
    //create StudentDAO class
    public StudentController() throws SQLException {
        //static method
        studentModel = StudentModel.getStudentModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //can be doPost base on UI design
        //TO-DO:
    }

    public void insertStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //get user input
        int id = Integer.parseInt(request.getParameter(""));
        String name = request.getParameter("");
        String DOB = request.getParameter("");
        int batch = Integer.parseInt(request.getParameter(""));

        //pass to model class to insert to DB
        student = new Student(id,name,DOB,batch);
        result = studentModel.insert(student);

        if(result){
            //do sth
        }
        else{
            //do sth
        }
    }

    public void getSingleStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //get Id from input
        String id = request.getParameter("");
        //fetch student record
        student = studentModel.get(Integer.parseInt(id));
        request.setAttribute("employee", student);

        //convert to JSON file
        Gson gson = new Gson();
        String json = gson.toJson(student);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //handle the JSON text here
    }

    public void getListStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //fetch all record and store as list
        List<Student> studentsList = studentModel.get();
        request.setAttribute("list", studentsList);

        //convert to JSON
        Gson gson = new Gson();
        String json = gson.toJson(studentsList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //handle the JSON text here
    }

    public void checkStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //get Id from input
        String id = request.getParameter("");
        //fetch student record
        student = studentModel.get(Integer.parseInt(id));
        if(student==null){
            //do sth
        }
        else{
            //do sth
        }
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter(""));
        String name = request.getParameter("");
        String DOB = request.getParameter("");
        int batch = Integer.parseInt(request.getParameter(""));

        student = new Student(id,name,DOB,batch);
        int result = studentModel.update(student);

        if(result>0){
            //do sth
        }
        else{
            //do sth
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = studentModel.delete(id);

        if(result > 0){
            //do sth
        }
        else{
            //do sth
        }
    }
}
