package andy.com.books.javaPersistenceWithMybatis3.chapter1;

import andy.com.books.javaPersistenceWithMybatis3.chapter1.domain.Student;
import andy.com.books.javaPersistenceWithMybatis3.chapter1.service.StudentService;
import andy.com.books.javaPersistenceWithMybatis3.common.MybatisSqlSessionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class Test1 {

    private static StudentService studentService;

    private static String conf1 = "books/PersistenceWithMybatis3/chapter1/mybatis-config-1.xml";
    private static String db1 = "db1";
    private static String db2 = "db2";

    @BeforeClass
    public static void setUp(){
        MybatisSqlSessionFactory.init(conf1);
        studentService = new StudentService();
    }

    @Test
    public void testFindAllStudents(){
        List<Student> studentList = studentService.findAllStudents(db1);
        System.out.println(studentList);
    }

    @Test
    public void testFindStudentById() {
        Student student = studentService.findStudentById(db1,1);
        Assert.assertNotNull(student);
        System.out.println(student);
    }
    @Test
    public void testCreateStudent() {
        Student student = new Student();
        int id = 3;
        student.setStudId(id);
        student.setName("student_"+id);
        student.setEmail("student_"+id+"gmail.com");
        student.setDob(new Date());
        studentService.createStudent(db2,student);
        Student newStudent = studentService.findStudentById(db2,id);
        Assert.assertNotNull(newStudent);
    }
}
