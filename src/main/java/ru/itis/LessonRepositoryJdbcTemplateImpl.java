package ru.itis;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Lesson;
import ru.itis.models.Student;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

public class LessonRepositoryJdbcTemplateImpl implements LessonRepository {
    //language=SQL
    private static final String SQL_SELECT_ALL = "select l.course_id as cid, c.date as course_date, * from lesson as l left join course c on l.course_id = c.course_id";

    //language=SQL
    private static final String SQL_SELECT_ALL_BY_NAME = "select l.course_id as cid, c.date as course_date,* from lesson as l left join course c on l.course_id = c.course_id where lesson_name = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select l.course_id as cid, c.date as course_date,* from lesson as l left join course c on l.course_id = c.course_id where lesson_id = ?";

    //language=SQL
    private static final String SQL_UPDATE_BY_ID = "update lesson set lesson_name = ?, date = ?, course_id = ? where lesson_id = ?";

    //language=SQL
    private static final String SQL_INSERT_LESSON = "insert into lesson(lesson_name, date, course_id) values (?, ?, ?)";

    //language=SQL
    private static final String SQL_DELETE_LESSON = "delete from lesson where lesson_id = ?";

    private JdbcTemplate jdbcTemplate;

    public LessonRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Lesson> lessonRowMapper = (row, rowNumber) -> {
        int id = row.getInt("lesson_id");
        String name = row.getString("lesson_name");
        String date = row.getString("date");
        int courseId = row.getInt("course_id");
        String courseName = row.getString("course_name");
        String courseDate = row.getString("course_date");
        int courseTeacherId = row.getInt("teacher_id");

        Lesson lesson = new Lesson(id, name, date, new Course(courseId, courseName, courseDate, courseTeacherId, new ArrayList<>()));
        return lesson;
    };

    public List<Lesson> findAll(){
        return jdbcTemplate.query(SQL_SELECT_ALL, lessonRowMapper, null);
    }

    @Override
    public List<Lesson> findAllByLessonName(String searchName) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_NAME, lessonRowMapper, searchName);
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        List<Lesson> res = jdbcTemplate.query(SQL_SELECT_BY_ID, lessonRowMapper, id);
        if(res.size()==1){
            return Optional.of(res.get(0));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LESSON, new String[] {"lesson_id"});

            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getDate());
            statement.setInt(3, lesson.getCourse().getId());

            return statement;
        }, keyHolder);

        lesson.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, lesson.getName(),lesson.getDate(),lesson.getCourse().getId(),lesson.getId());
    }

    @Override
    public void delete(Lesson lesson) {
        jdbcTemplate.update(SQL_DELETE_LESSON,lesson.getId());
    }
}
