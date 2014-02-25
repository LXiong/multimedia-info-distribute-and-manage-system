alter table VIDEOS modify name NVARCHAR2(100) ;

delete from db_pm_version;
insert into db_pm_version values('201109191611');