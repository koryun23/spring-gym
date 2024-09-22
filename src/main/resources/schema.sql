alter table if exists TRAINEE drop constraint if exists FK_TRAINEE_USER_ID_USER_ID
alter table if exists TRAINER drop constraint if exists FK_TRAINER_SPECIALIZATION_ID_TRAINING_TYPE_ID
alter table if exists TRAINER drop constraint if exists FK_TRAINER_USER_ID_USER_ID
alter table if exists TRAINING drop constraint if exists FK_TRAINING_TRAINEE_ID_TRAINEE_ID
alter table if exists TRAINING drop constraint if exists FK_TRAINING_TRAINER_ID_TRAINER_ID
alter table if exists TRAINING drop constraint if exists FK_TRAINING_TRAINING_TYPE_ID_TRAINING_TYPE_ID
drop table if exists TRAINEE cascade
drop table if exists TRAINER cascade
drop table if exists TRAINING cascade
drop table if exists TRAINING_TYPE cascade
drop table if exists USERS cascade
drop sequence if exists TRAINEE_SEQUENCE
drop sequence if exists TRAINER_SEQUENCE
drop sequence if exists TRAINING_SEQUENCE
drop sequence if exists TRAINING_TYPE_SEQUENCE
drop sequence if exists USER_SEQUENCE
create sequence TRAINEE_GENERATOR start with 1 increment by 1
create sequence TRAINER_GENERATOR start with 1 increment by 1
create sequence TRAINING_SEQUENCE start with 1 increment by 1
create sequence TRAINING_TYPE_SEQUENCE start with 1 increment by 1
create sequence USER_SEQUENCE start with 1 increment by 1
create table TRAINEE (date_of_birth timestamp(6) not null, id bigint not null primary key DEFAULT nextval('trainee_sequence'), user_id bigint not null unique, address varchar(255) not null)
create table TRAINER (id bigint not null primary key DEFAULT nextval('trainer_sequence'), specialization_id bigint not null, user_id bigint not null unique)
create table TRAINING (date timestamp(6) not null, duration bigint not null, id bigint not null primary key DEFAULT nextval('training_sequence'), trainee_id bigint, trainer_id bigint, training_type_id bigint not null, training_name varchar(255) not null)
create table TRAINING_TYPE (id bigint not null primary key DEFAULT nextval('training_type_sequence'), training_type varchar(255) check (training_type in ('STRENGTH_TRAINING','AEROBIC','BODYBUILDING','FLEXIBILITY_TRAINING','WEIGHTLIFTING')))
create table USERS (is_active boolean, id bigint not null primary key DEFAULT nextval('user_sequence'), first_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, username varchar(255) not null)
alter table if exists TRAINEE add constraint FK_TRAINEE_USER_ID_USER_ID foreign key (user_id) references USERS
alter table if exists TRAINER add constraint FK_TRAINER_SPECIALIZATION_ID_TRAINING_TYPE_ID foreign key (specialization_id) references TRAINING_TYPE
alter table if exists TRAINER add constraint FK_TRAINER_USER_ID_USER_ID foreign key (user_id) references USERS
alter table if exists TRAINING add constraint FK_TRAINING_TRAINEE_ID_TRAINEE_ID foreign key (trainee_id) references TRAINEE
alter table if exists TRAINING add constraint FK_TRAINING_TRAINER_ID_TRAINER_ID foreign key (trainer_id) references TRAINER
alter table if exists TRAINING add constraint FK_TRAINING_TRAINING_TYPE_ID_TRAINING_TYPE_ID foreign key (training_type_id) references TRAINING_TYPE