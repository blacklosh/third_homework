package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Student;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

public class CourseRepositoryJdbcTemplateImpl implements CourseRepository {
    //language=SQL
    private static final String SQL_SELECT_ALL
            = "select * from (select a.course_id as cid, * from course as a left join student_on_course as b on a.course_id = b.course_id order by a.course_id) as d left join student e " +
            "on d.student_id = e.student_id";

    //language=SQL
    private static final String SQL_SELECT_ALL_BY_NAME = SQL_SELECT_ALL+" where d.course_name = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL+" where d.cid = ?";

    //language=SQL
    private static final String SQL_UPDATE_BY_ID = "update course set course_name = ?, date = ?, teacher_id = ? where course_id = ?";

    //language=SQL
    private static final String SQL_INSERT_COURSE = "insert into course(course_name, date, teacher_id) values (?, ?, ?)";

    //language=SQL
    private static final String SQL_INSERT_STUDENT_TO_COURSE = "insert into student_on_course(course_id, student_id) values (?, ?)";

    //language=SQL
    private static final String SQL_DELETE_STUDENT_FROM_COURSE = "delete from student_on_course where course_id = ?";

    //language=SQL
    private static final String SQL_DELETE_COURSE = "delete from course where course_id = ?";

    private JdbcTemplate jdbcTemplate;

    public CourseRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Course> courseRowMapper = (row, rowNumber) -> {
        int id = row.getInt("course_id");
        String name = row.getString("course_name");
        String date = row.getString("date");
        int teacherId = row.getInt("teacher_id");

        Course course = new Course(id, name, date, teacherId, null);
        course.setStudents(new ArrayList<>());
        return course;
    };

    private final ResultSetExtractor<Map<Integer, List<Student>>> courseStudentsResultSetExtractor = resultSet -> {
        Map<Integer, List<Student>> data = new LinkedHashMap<>();
        while (resultSet.next()) {
            Integer courseId = resultSet.getInt("course_id");
            data.putIfAbsent(courseId, new ArrayList<Student>());
            Integer studentId = resultSet.getInt("student_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Integer groupId = resultSet.getInt("group_id");
            Student s = new Student(studentId, firstName, lastName, groupId, null);
            data.get(courseId).add(s);
        }
        return data;
    };

    private final ResultSetExtractor<List<Course>> courseWithStudentsResultSetExtractor = resultSet -> {
        Map<Course, List<Student>> data = new LinkedHashMap<>();
        while (resultSet.next()) {
            Integer courseId = resultSet.getInt("course_id");
            String name = resultSet.getString("course_name");
            String date = resultSet.getString("date");
            Integer teacherId = resultSet.getInt("teacher_id");
            Course course = new Course(courseId, name, date, teacherId, null);
            data.putIfAbsent(course, new ArrayList<Student>());
            Integer studentId = resultSet.getInt("student_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Integer groupId = resultSet.getInt("group_id");
            Student s = new Student(studentId, firstName, lastName, groupId, null);
            data.get(course).add(s);
        }
        List<Course> result = new ArrayList<>();
        for(Map.Entry<Course, List<Student>> e : data.entrySet()){
            Course course = e.getKey();
            List<Student> studs = e.getValue();
            course.setStudents(studs);
            result.add(course);
        }
        return result;
    };

    public List<Course> findAll(){
       return jdbcTemplate.query(SQL_SELECT_ALL, courseWithStudentsResultSetExtractor, null);
    }

    @Override
    public List<Course> findAllByCourseName(String searchName) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_NAME, courseWithStudentsResultSetExtractor, searchName);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        List<Course> res = jdbcTemplate.query(SQL_SELECT_BY_ID, courseWithStudentsResultSetExtractor, id);
        if(res.size()==1){
            return Optional.of(res.get(0));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COURSE, new String[] {"course_id"});

            statement.setString(1, course.getName());
            statement.setString(2, course.getDate());
            statement.setInt(3, course.getTeacherId());

            return statement;
        }, keyHolder);

        course.setId(keyHolder.getKey().intValue());

        for(Student student : course.getStudents()){
            jdbcTemplate.update(SQL_INSERT_STUDENT_TO_COURSE,course.getId(),student.getId());
        }
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, course.getName(),course.getDate(),course.getTeacherId(),course.getId());
        jdbcTemplate.update(SQL_DELETE_STUDENT_FROM_COURSE, course.getId());
        for(Student student : course.getStudents()){
            jdbcTemplate.update(SQL_INSERT_STUDENT_TO_COURSE,course.getId(),student.getId());
        }
    }

    @Override
    public void delete(Course course) {
        jdbcTemplate.update(SQL_DELETE_STUDENT_FROM_COURSE, course.getId());
        jdbcTemplate.update(SQL_DELETE_COURSE,course.getId());
    }
}
