package andy.com.books.javaPersistenceWithMybatis3.chapter1.service;

import andy.com.books.javaPersistenceWithMybatis3.chapter1.domain.Student;
import andy.com.books.javaPersistenceWithMybatis3.chapter1.mappers.StudentMapper;
import andy.com.books.javaPersistenceWithMybatis3.common.MybatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentService {

    public List<Student> findAllStudents(String envId) {
        SqlSession sqlSession = MybatisSqlSessionFactory.openSession(envId);
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findAllStudents();

        } finally {
            sqlSession.close();
        }
    }

    public Student findStudentById(String envId, Integer studId) {
        SqlSession sqlSession = MybatisSqlSessionFactory.openSession(envId);
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findStudentById(studId);
        } finally {
            sqlSession.close();
        }
    }

    public void createStudent(String envId,Student student) {
        SqlSession sqlSession =
                MybatisSqlSessionFactory.openSession(envId);
        try {
            StudentMapper studentMapper =
                    sqlSession.getMapper(StudentMapper.class);
            studentMapper.insertStudent(student);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
