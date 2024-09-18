alter table if exists TRAINEE drop constraint if exists FK2t3ner97nypqjjvvg8fnycrk7
alter table if exists TRAINER drop constraint if exists FKga4ig2v1qwvr3stbisoqvgffm
alter table if exists TRAINER drop constraint if exists FKqx3pjie9482mjult4hombc9y3
alter table if exists TRAINING drop constraint if exists FKqetaw250jxb3witc7rewif68g
alter table if exists TRAINING drop constraint if exists FKsv3ghh6nrfnidfno5mhxctr8b
alter table if exists TRAINING drop constraint if exists FK7os72ujqpaip4r0fyptx79b7o
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
create table TRAINEE (date_of_birth timestamp(6) not null, id bigint not null, user_id bigint not null unique, address varchar(255) not null, primary key (id))
create table TRAINER (id bigint not null, specialization_id bigint not null, user_id bigint not null unique, primary key (id))
create table TRAINING (date timestamp(6) not null, duration bigint not null, id bigint not null, trainee_id bigint, trainer_id bigint, training_type_id bigint not null, training_name varchar(255) not null, primary key (id))
create table TRAINING_TYPE (id bigint not null, training_type varchar(255) check (training_type in ('STRENGTH_TRAINING','AEROBIC','BODYBUILDING','FLEXIBILITY_TRAINING','WEIGHTLIFTING')), primary key (id))
create table USERS (is_active boolean, id bigint not null, first_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id))
alter table if exists TRAINEE add constraint FK2t3ner97nypqjjvvg8fnycrk7 foreign key (user_id) references USERS
alter table if exists TRAINER add constraint FKga4ig2v1qwvr3stbisoqvgffm foreign key (specialization_id) references TRAINING_TYPE
alter table if exists TRAINER add constraint FKqx3pjie9482mjult4hombc9y3 foreign key (user_id) references USERS
alter table if exists TRAINING add constraint FKqetaw250jxb3witc7rewif68g foreign key (trainee_id) references TRAINEE
alter table if exists TRAINING add constraint FKsv3ghh6nrfnidfno5mhxctr8b foreign key (trainer_id) references TRAINER
alter table if exists TRAINING add constraint FK7os72ujqpaip4r0fyptx79b7o foreign key (training_type_id) references TRAINING_TYPE