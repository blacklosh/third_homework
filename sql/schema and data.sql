﻿create table teacher
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

insert into teacher(first_name, last_name, exp)
VALUES ('Иван','Иванов',1);
insert into teacher(first_name, last_name, exp)
VALUES ('Пётр','Петров',2);
insert into teacher(first_name, last_name, exp)
VALUES ('Василий','Сидоров',3);

insert into course(course_name, date, teacher_id)
values ('1C','01.02.23',1);
insert into course(course_name, date, teacher_id)
values ('Java','04.05.26',1);
insert into course(course_name, date, teacher_id)
values ('PHP','09.08.27',2);

insert into lesson(lesson_name, date,course_id)
values ('1c-1','x',1);
insert into lesson(lesson_name, date,course_id)
values ('1c-2','y',1);
insert into lesson(lesson_name, date,course_id)
values ('1c-3','z',1);
insert into lesson(lesson_name, date,course_id)
values ('j1','w',2);
insert into lesson(lesson_name, date,course_id)
values ('j2','v',2);
insert into lesson(lesson_name, date,course_id)
values ('php1','g',3);

insert into student(first_name, last_name, group_id)
values ('Имя1','Ф1',001);
insert into student(first_name, last_name, group_id)
values ('Имя2','Ф2',001);
insert into student(first_name, last_name, group_id)
values ('Имя3','Ф3',002);

insert into student_on_course(student_id,course_id)
values (1,1);
insert into student_on_course(student_id,course_id)
values (1,2);
insert into student_on_course(student_id,course_id)
values (2,2);
insert into student_on_course(student_id,course_id)
values (2,3);
insert into student_on_course(student_id,course_id)
values (3,1);