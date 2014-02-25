CREATE table "PACKAGES" (
    "VERSION"     VARCHAR2(10) NOT NULL,
    "NAME"        VARCHAR2(100) NOT NULL,
    "ILLUSTRATE"  VARCHAR2(1000),
    "CREATE_TIME" TIMESTAMP,
    "UPDATE_TIME" TIMESTAMP,
    constraint  "PACKAGES_PK" primary key ("VERSION")
)
/
alter table "STB_WARNING_INFO" add SEVERITY varchar(20) default '1';
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'defaultVolume', '');
delete from db_mc_version;
insert into db_mc_version values('201109191558');