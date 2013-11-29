# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table note (
  id                        bigint not null,
  text                      varchar(255),
  constraint pk_note primary key (id))
;

create table project (
  id                        bigint not null,
  name                      varchar(255),
  track_count               integer,
  bpm                       integer,
  owner_id                  bigint,
  constraint pk_project primary key (id))
;

create table track (
  id                        bigint not null,
  title                     varchar(255),
  assigned_to_id            bigint,
  path                      varchar(255),
  project_id                bigint,
  constraint pk_track primary key (id))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  state                     varchar(255),
  city                      varchar(255),
  zip                       varchar(255),
  genres                    varchar(255),
  sounds_like               varchar(255),
  bio                       varchar(255),
  constraint pk_user primary key (id))
;


create table project_user (
  project_id                     bigint not null,
  user_id                        bigint not null,
  constraint pk_project_user primary key (project_id, user_id))
;

create table project_note (
  project_id                     bigint not null,
  note_id                        bigint not null,
  constraint pk_project_note primary key (project_id, note_id))
;

create table track_note (
  track_id                       bigint not null,
  note_id                        bigint not null,
  constraint pk_track_note primary key (track_id, note_id))
;
create sequence note_seq;

create sequence project_seq;

create sequence track_seq;

create sequence user_seq;

alter table project add constraint fk_project_owner_1 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_project_owner_1 on project (owner_id);
alter table track add constraint fk_track_assignedTo_2 foreign key (assigned_to_id) references user (id) on delete restrict on update restrict;
create index ix_track_assignedTo_2 on track (assigned_to_id);
alter table track add constraint fk_track_project_3 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_track_project_3 on track (project_id);



alter table project_user add constraint fk_project_user_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_user add constraint fk_project_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table project_note add constraint fk_project_note_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_note add constraint fk_project_note_note_02 foreign key (note_id) references note (id) on delete restrict on update restrict;

alter table track_note add constraint fk_track_note_track_01 foreign key (track_id) references track (id) on delete restrict on update restrict;

alter table track_note add constraint fk_track_note_note_02 foreign key (note_id) references note (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists note;

drop table if exists project;

drop table if exists project_user;

drop table if exists project_note;

drop table if exists track;

drop table if exists track_note;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists note_seq;

drop sequence if exists project_seq;

drop sequence if exists track_seq;

drop sequence if exists user_seq;

