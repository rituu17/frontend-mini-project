create database miniprojecttwo;
use miniprojecttwo;

-------------------------------------------------------------------------------------------------------- 
 -- Roles table  
create table roles (roll_id int not null auto_increment , roll_name varchar(255), primary key (roll_id));
insert into roles (roll_name) values ('Admin');
insert into roles (roll_name) values ('Doctor');
insert into roles (roll_name) values ('Patient');
insert into roles (roll_name) values ('Nurse');
insert into roles (roll_name) values('Receptionist');
select * from roles;

--------------------------------------------------------------------------------------------------------
-- Users table
create table users ( 
user_id bigint not null auto_increment, roll_id int not null, firstname varchar(255) not null, 
lastname varchar(255) not null, email varchar(255) not null, mobile bigint not null,
password varchar(255) not null, active int not null,  profile_url varchar(255),
city varchar(100), primary key (user_id) );
select * from users;
insert into users 
(roll_id, firstname, lastname, email, mobile, password, active, specialization, profile_url,city)
values(3, 'Stuti', 'Shah', 'stuti@cybage.com', 789654123, 'pass321', 1, null, 'Nagpur');

--------------------------------------------------------------------------------------------------------
-- Appointments table
create table appointments (
appointment_id bigint not null auto_increment, patient_id bigint not null, doctor_id bigint not null,
appointment_date date not null, appointment_time time not null, feedback mediumtext, ratings int, 
patient_notes mediumtext, primary key (appointment_id) );
select * from appointments;

--------------------------------------------------------------------------------------------------------
-- Specialization table
create table specialization (specialization_id bigint not null auto_increment, category text not null, 
primary key(specialization_id) );
select * from specialization;
insert into specialization (category) values ('Cardiologist');
insert into specialization (category) values ('General Pediatrician');
insert into specialization (category) values ('Physician');
insert into specialization (category) values ('Dermatologists');
insert into specialization (category) values ('Ophthalmologists');
insert into specialization (category) values ('Neurologists');
insert into specialization (category) values ('Radiologists');
insert into specialization (category) values ('General Surgeons');
insert into specialization (category) values ('Oncologists');
insert into specialization (category) values ('Allergists');

--------------------------------------------------------------------------------------------------------
-- Doctor's Specialization table
create table doctors_specialization (doctors_specialization_id bigint not null auto_increment, 
specialization_id bigint not null, doctor_id bigint not null, primary key(doctors_specialization_id));
select * from doctors_specialization;
insert into doctors_specialization(specialization_id, doctor_id)values(3, 1);
insert into doctors_specialization(specialization_id, doctor_id)values(1, 1);
insert into doctors_specialization(specialization_id, doctor_id)values(6, 4);
insert into doctors_specialization(specialization_id, doctor_id)values(6, 3);
insert into doctors_specialization(specialization_id, doctor_id)values(9, 4);
insert into doctors_specialization(specialization_id, doctor_id)values(1, 3);
insert into doctors_specialization(specialization_id, doctor_id)values(8, 3);
insert into doctors_specialization(specialization_id, doctor_id)values(7, 1);

select * from users where u.user_id = 1;
select doctor_id from doctors_specialization where specialization_id = (select specialization_id from specialization where category="Cardiologist");
select doctor_id from doctors_specialization ;
select users.firstname from ((users inner join specialization on users.user_id = specialization.specialization_id)
inner join specialization doctors_specialization.specialization_id = specialization.specialization_id where specialization.category = "Cardiologist");

select * from users where users.user_id in (
select doctor_id from doctors_specialization ds inner join
specialization s on ds.specialization_id = s.specialization_id where s.category = "Cardiologist");


















