package andy.com.books.javaPersistenceWithMybatis3.chapter1.mappers;

import andy.com.books.javaPersistenceWithMybatis3.chapter1.domain.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> findAllStudents();
    Student findStudentById(Integer id);
    void insertStudent(Student student);
}
