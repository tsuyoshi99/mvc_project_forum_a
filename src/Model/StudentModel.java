package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * StudentModel
 */
public class StudentModel {
    private static Connection conn = null;
    private static StudentModel instance = null;
    private StudentModel(){}

    public static StudentModel getStudentModel() throws SQLException {
        if (instance!=null) {
            return instance;
        }else {
            conn = DB.getConnection();
            conn.createStatement().execute("create table if not exists student (id smallint auto_increment,name varchar(50),DOB varchar(50),batch smallint,primary key(id));");
            instance = new StudentModel();
            return instance;
        }
    }

    public Student get(int id) throws SQLException {
        PreparedStatement ps =  conn.prepareStatement("select * from student where id = ?;");
        ps.setInt(1, 1);
        ResultSet rs =  ps.executeQuery();
        rs.next();
        Student student = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
        return student;
    }

    public LinkedList<Student> get() throws SQLException {
        final Statement st = conn.createStatement();
        final ResultSet rs =  st.executeQuery("select * from student;");
        final LinkedList<Student> students = new LinkedList<>();
        while (rs.next()) {
            students.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
        }
        return students;
    }
    

    public boolean insert (final Student student) throws SQLException {
        final PreparedStatement ps = conn.prepareStatement("insert into student(name,DOB,batch) values (?,?,?);");
        ps.setString(1, student.getName());
        ps.setString(2, student.getDOB());
        ps.setInt(3,student.getBatch());
        return ps.execute();
    }

    public int[] insert (final LinkedList<Student> students) throws SQLException{
        final PreparedStatement ps = conn.prepareStatement("insert into student (name,DOB,batch) values (?,?,?);");
        for (final Student student:students){
            ps.setString(1, student.getName());
            ps.setString(2, student.getDOB());
            ps.setInt(3,student.getBatch());
            ps.addBatch();    
        }
        return ps.executeBatch();
    }

    public int update(final Student student) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("update student set name=?,DOB=?,batch=? where id=?");
        ps.setString(1, student.getName());
        ps.setString(2, student.getDOB());
        ps.setInt(3,student.getBatch());
        ps.setInt(4, student.getId());
        return ps.executeUpdate();
    }

    public int delete(final int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from student where id=?;");
        ps.setInt(1,id);
        return ps.executeUpdate();
    }
}