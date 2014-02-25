alter table "STB" add DOWNLOAD_STATUS VARCHAR2(30) default 'updated';
alter table "STB_WARNING_INFO" add STATUS VARCHAR2(50) default 'waiting_for_processing';
alter table "STB_WARNING_INFO" drop column details;
alter table "STB_WARNING_INFO" add DETAILS varchar2(300);

DELETE FROM db_mc_version;
INSERT INTO db_mc_version VALUES('201108191355');