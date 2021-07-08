package ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.models.Course;
import ru.itis.models.Student;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(Main.class.getClassLoader().getResource("application.properties").getFile()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        DataSource dataSource = new HikariDataSource(config);

        CourseRepository courseRepository = new CourseRepositoryJdbcTemplateImpl(dataSource);

        testAll(courseRepository);
    }

    private static void testAll(CourseRepository courseRepository) {
        // Test findAll();
        System.out.println(courseRepository.findAll());

        // Test findById();
        System.out.println(courseRepository.findById(1));
        System.out.println(courseRepository.findById(5));

        // Test findAllByCourseName();
        System.out.println(courseRepository.findAllByCourseName("PHP"));
        System.out.println(courseRepository.findAllByCourseName("Python"));

        // Test update();
        Course c = courseRepository.findById(1).get();
        c.setName("other");
        courseRepository.update(c);
        System.out.println(courseRepository.findById(1));
        c.setName("1C");
        courseRepository.update(c);
        System.out.println(courseRepository.findById(1));

        // Test save();
        List<Student> l = new ArrayList<>();
        l.add(new Student(2,"","",0,null));
        l.add(new Student(3,"","",0,null));
        Course d = new Course("C#","--/--/--",2, l);
        courseRepository.save(d);
        System.out.println(courseRepository.findAllByCourseName("C#"));
        System.out.println(d.getId());

        // Test update(); с изменением студентов
        l.clear();
        l.add(new Student(1,"","",0,null));
        d.setStudents(l);
        courseRepository.update(d);
        System.out.println(courseRepository.findAllByCourseName("C#"));
        System.out.println(d.getId());

        // Test delete();
        courseRepository.delete(d);
        System.out.println(courseRepository.findAll());
    }
}
