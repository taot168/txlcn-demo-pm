drop table t_logger;

/*==============================================================*/
/* Table: t_logger                                              */
/*==============================================================*/
create table t_logger (
   id                   INT4                 not null,
   group_id             VARCHAR(64)          null,
   unit_id              VARCHAR(32)          null,
   tag                  VARCHAR(50)          null,
   content              VARCHAR(1024)        null,
   create_time          VARCHAR(30)          null,
   app_name             VARCHAR(128)         null
);

comment on table t_logger is
't_logger';

comment on column t_logger.id is
'id';

comment on column t_logger.group_id is
'group_id';

comment on column t_logger.unit_id is
'unit_id';

comment on column t_logger.tag is
'tag';

comment on column t_logger.content is
'content';

comment on column t_logger.create_time is
'create_time';

comment on column t_logger.app_name is
'app_name';




drop table t_demo;

/*==============================================================*/
/* Table: t_demo                                                */
/*==============================================================*/
create table t_demo (
   id                   INT4                 not null,
   kid                  VARCHAR(45)          null,
   demo_field           VARCHAR(255)         null,
   group_id             VARCHAR(64)          null,
   unit_id              VARCHAR(32)          null,
   app_name             VARCHAR(128)         null,
   create_time          TIMESTAMP                 null
);

comment on table t_demo is
't_demo';

comment on column t_demo.id is
'id';

comment on column t_demo.kid is
'kid';

comment on column t_demo.demo_field is
'demo_field';

comment on column t_demo.group_id is
'group_id';

comment on column t_demo.unit_id is
'unit_id';

comment on column t_demo.app_name is
'app_name';

comment on column t_demo.create_time is
'create_time';

