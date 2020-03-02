package Controller;

import Model.Student;
import Model.StudentModel;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/studentController/*")
public class StudentController extends HttpServlet {
    Student student = null;
    StudentModel studentModel = null;
    Gson gson = null;

    boolean result;
    //create StudentDAO class
    public StudentController() throws SQLException {
        //static method
        studentModel = StudentModel.getStudentModel();
    }

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException{
        response.setContentType("application/json");
        String res = gson.toJson(obj);

        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

    // GET/mvc_project_forum_a/studentController/
    // GET/mvc_project_forum_a/studentController/id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //getting URL pattern
        student = null;
        String pathInfo = req.getPathInfo();

        //send list of all record
        if(pathInfo == null || pathInfo.equals("/")){
            try {
                LinkedList<Student> students = getListStudent(req,resp);
                sendAsJson(resp, students);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        //split URL pattern in order to get id
        String[] urlSplits = pathInfo.split("/");
        if(urlSplits.length!=2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //check if there an id that match
        int id = Integer.parseInt(urlSplits[1]);
        try{
            String result = checkStudent(req,resp,id);
            if(result=="null"){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        //get the student object and send as JSON file
        try {
            student = getSingleStudent(req,resp,id);
            sendAsJson(resp, student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // POST/mvc_project_forum_a/studentController/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //getting URL pattern
        student = null;
        String pathInfo = req.getPathInfo();

        //read Http request body
        if(pathInfo == null || pathInfo.equals("/")){
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = req.getReader();
            String reqline;
            while ((reqline = reader.readLine()) != null) {
                buffer.append(reqline);
            }
            //convert Http request body to JSON
            String payload = buffer.toString();
            student = gson.fromJson(payload, Student.class);
        }
        //insert into DB and send response as JSON
        try {
            studentModel.insert(student);
            sendAsJson(resp,student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PUT/mvc_project_forum_a/studentController/id
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //check URL pattern
        student = null;
        String pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //split URL pattern in order to get id
        String[] urlSplits = pathInfo.split("/");
        if(urlSplits.length!=2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //check if there an id that match
        int id = Integer.parseInt(urlSplits[1]);
        try{
            String result = checkStudent(req,resp,id);
            if(result=="null"){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        //read http request body
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String reqline;
        while ((reqline = reader.readLine()) != null) {
            buffer.append(reqline);
        }
        //convert to student object
        String payload = buffer.toString();
        student = gson.fromJson(payload, Student.class);

        //update in DB and send response as JSON
        try {
            studentModel.update(student);
            sendAsJson(resp,student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE/mvc_project_forum_a/studentController/id
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //check URL pattern
        student = null;
        String pathInfo = req.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){
            sendAsJson(resp, student);
            return;
        }

        //split URL pattern in order to get id
        String[] urlSplits = pathInfo.split("/");
        if(urlSplits.length!=2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //delete in DB and send response as JSON
        int id = Integer.parseInt(urlSplits[1]);
        try {
            student = getSingleStudent(req,resp,id);
            studentModel.delete(id);
            sendAsJson(resp, student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public Student insertStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //get user input
        int id = Integer.parseInt(request.getParameter(""));
        String name = request.getParameter("");
        String DOB = request.getParameter("");
        int batch = Integer.parseInt(request.getParameter(""));

        //pass to model class to insert to DB
        student = new Student(id,name,DOB,batch);
        result = studentModel.insert(student);

        if(result){
            return student;
        }
        else{
            return null;
        }
    }*/

    public Student getSingleStudent(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, SQLException {
        //fetch student record
        student = studentModel.get(id);
        return student;
    }

    public LinkedList<Student> getListStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //fetch all record and store as list
        LinkedList<Student> studentsList = studentModel.get();
        return studentsList;
    }

    public String checkStudent(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, SQLException {
        //get Id from input
        //String id = request.getParameter("");
        //fetch student record
        student = studentModel.get(id);
        if(student==null){
            return "null";
        }
        else{
            return "true";
        }
    }

    /*public void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
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
    }*/

    /*public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = studentModel.delete(id);

        if(result > 0){
            //do sth
        }
        else{
            //do sth
        }
    }*/
}
