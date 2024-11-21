create sequence if not exists TRAINEE_SEQUENCE start with 1 increment by 1;
create sequence if not exists TRAINER_SEQUENCE start with 1 increment by 1;
create sequence if not exists TRAINING_SEQUENCE start with 1 increment by 1;
create sequence if not exists TRAINING_TYPE_SEQUENCE start with 1 increment by 1;
create sequence if not exists USER_SEQUENCE start with 1 increment by 1;
create sequence if not exists USER_SUFFIX_SEQUENCE start with 1 increment by 1;
create sequence if not exists USER_ROLE_SEQUENCE start with 1 increment by 1;

create table if not exists USERS (
    is_active boolean not null,
    id bigint not null primary key DEFAULT nextval('user_sequence'),
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null
);

create table if not exists USER_SUFFIX (
    id bigint not null primary key DEFAULT nextval('user_suffix_sequence'),
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    suffix bigint not null
);

create table if not exists USER_ROLE (
    id bigint not null primary key DEFAULT nextval('user_role_sequence'),
    user_id bigint not null,
    role varchar(50) not null,
    foreign key (user_id) references users(id)
);

create table if not exists TRAINING_TYPE (
     id bigint not null primary key DEFAULT nextval('training_type_sequence'),
     training_type varchar(255) check (training_type in ('STRENGTH_TRAINING','AEROBIC','BODYBUILDING','FLEXIBILITY_TRAINING','WEIGHTLIFTING'))
);

create table if not exists TRAINEE (
    date_of_birth timestamp(6),
    id bigint not null primary key DEFAULT nextval('trainee_sequence'),
    user_id bigint not null unique,
    address varchar(255),
    foreign key (user_id) references users(id)
);

create table if not exists TRAINER (
    id bigint not null primary key DEFAULT nextval('trainer_sequence'),
    specialization_id bigint not null,
    user_id bigint not null unique,
    foreign key (specialization_id) references training_type(id),
    foreign key (user_id) references users(id));

create table if not exists TRAINING (
    date timestamp(6) not null,
    duration bigint not null,
    id bigint not null primary key DEFAULT nextval('training_sequence'),
    training_name varchar(255) not null,
    trainee_id bigint not null,
    trainer_id bigint not null,
    training_type_id bigint not null,
    foreign key (trainee_id) references trainee(id),
    foreign key (trainer_id) references trainer(id),
    foreign key (training_type_id) references training_type(id)
);
