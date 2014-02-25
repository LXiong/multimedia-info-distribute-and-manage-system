CREATE TABLE publish_task
(
	id NUMBER ,
	policy_id NUMBER,
	publish_time DATE,
	publish_areas NVARCHAR2 (200),
	status NVARCHAR2 (20) DEFAULT 'pending',
	created_at DATE,
	primary key ( id )
)
/
CREATE SEQUENCE seq_publish_task
/
delete from db_pm_version;
insert into db_pm_version values('201108261310');
commit;