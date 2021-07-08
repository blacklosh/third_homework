create table teacher
(
    teacher_id serial primary key,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    exp integer
);

create table course
(
    course_id serial primary key,
    course_name varchar(20) not null,
    date varchar(20) not null,
    teacher_id serial,
    foreign key (teacher_id) references teacher(teacher_id)
);

create table lesson
(
    lesson_id serial primary key,
    lesson_name varchar(20) not null,
    date varchar(20) not null,
    course_id serial,
    foreign key (course_id) references course(course_id)
);


create table student
(
    student_id serial primary key,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    group_id integer
);

create table student_on_course
(
    student_id serial,
    course_id serial,
    foreign key (student_id) references student(student_id),
    foreign key (course_id) references course(course_id)
);