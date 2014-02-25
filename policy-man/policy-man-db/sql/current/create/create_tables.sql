create table db_pm_version
(version varchar2(30))
/

CREATE TABLE area
(
	id NUMBER ,
	layoutid NUMBER,
	name VARCHAR2 (100),
	lcomment VARCHAR2 (200),
	type VARCHAR2 (2),
	left NUMBER,
	top NUMBER,
	height NUMBER,
	width NUMBER,
	zindex NUMBER,
	time_format VARCHAR2 (50),
	primary key ( id )
)
/

CREATE TABLE mm_authority
(
	id NUMBER ,
	type INTEGER,
	name VARCHAR2 (50),
	locale_zh_cn NVARCHAR2 (100),
	primary key ( id )
)
/

CREATE TABLE mm_auth_group
(
	id NUMBER ,
	name VARCHAR2 (50),
	locale_zh_cn NVARCHAR2 (100),
	primary key ( id )
)
/

CREATE TABLE category
(
	id NUMBER ,
	parent_id NUMBER,
	name NVARCHAR2 (100),
	description NVARCHAR2 (300),
	primary key ( id )
)
/

CREATE TABLE layout
(
	id NUMBER ,
	name VARCHAR2 (100),
	use_percent CHAR (1),
	width NUMBER,
	height NUMBER,
	lcomment VARCHAR2 (200),
	primary key ( id )
)
/

CREATE TABLE man_roles
(
	id NUMBER ,
	name NVARCHAR2 (30),
	description NVARCHAR2 (100),
	enabled CHAR (1) DEFAULT VALUE '1',
	creator NVARCHAR2 (30),
	created_at DATE,
	updator NVARCHAR2 (30),
	updated_at DATE,
	primary key ( id )
)
/

CREATE TABLE man_users
(
	username NVARCHAR2 (30),
	password VARCHAR2 (64),
	disp_name NVARCHAR2 (64),
	email VARCHAR2 (100),
	phone VARCHAR2 (20),
	enabled CHAR (1),
	last_login DATE,
	creator NVARCHAR2 (30),
	created_at DATE,
	updator NVARCHAR2 (30),
	updated_at DATE,
	primary key ( username )
)
/

CREATE TABLE policies
(
	id NUMBER ,
	name VARCHAR2 (100),
	comments VARCHAR2 (300),
	start_time DATE,
	end_time DATE,
	status VARCHAR2 (200),
	created_at DATE,
	created_by NVARCHAR2 (50),
	updated_at DATE,
	updated_by NVARCHAR2 (50),
	submit_at DATE,
	submit_by NVARCHAR2 (50),
	rejected_at DATE,
	rejected_by NVARCHAR2 (50),
	reason NVARCHAR2(250),
	audit_at DATE,
	audit_by NVARCHAR2 (50),
	published_at DATE,
	published_by NVARCHAR2 (50),
	primary key ( id )
)
/

CREATE TABLE policy_media
(
	policy_id NUMBER,
	name NVARCHAR2 (64),
	type CHAR (1),
	content NVARCHAR2 (200),
	length NUMBER,
	play_count NUMBER,
	primary key (		
			policy_id,
			name	)
)
/

CREATE TABLE publish_group
(
	policy_id NUMBER,
	type_id NUMBER,
	primary key (		
			policy_id,
			type_id	)
)
/

CREATE TABLE policy
(
	policyId NUMBER ,
	policy_number NUMBER,
	comments VARCHAR2 (300),
	begin_at DATE,
	end_at DATE,
	md5 VARCHAR2 (64),
	size_bytes NUMBER,
	update_time DATE,
	created_at DATE,
	file_path VARCHAR2 (100),
	primary key ( policyId )
)
/

CREATE TABLE publish_group
(
	id NUMBER ,
	published_at DATE,
	policy_number VARCHAR2 (50),
	policy_id NUMBER,
	type_id NUMBER,
	md5 VARCHAR2 (100),
	size_bytes NUMBER,
	file_path VARCHAR2 (200),
	primary key ( id )
)
/

CREATE TABLE man_role_auth
(
	role_id NUMBER,
	authority_id NUMBER,
	primary key (		
			role_id,
			authority_id	)
)
/

CREATE TABLE GROUPTYPE
(
	type_id NUMBER ,
	type_name VARCHAR2 (50),
	type_description VARCHAR2 (200),
	folder_name VARCHAR2 (50),
	primary key ( type_id )
)
/

CREATE TABLE timed_area
(
	id NUMBER ,
	layout_id NUMBER,
	policy_id NUMBER,
	name VARCHAR2 (100),
	lcomment VARCHAR2 (200),
	type VARCHAR2 (2),
	left NUMBER,
	top NUMBER,
	height NUMBER,
	width NUMBER,
	zindex NUMBER,
	font VARCHAR2 (20),
	color VARCHAR2 (7),
	bgcolor VARCHAR2 (7),
	time_format VARCHAR2 (50),
	primary key ( id )
)
/

CREATE TABLE timed_layout
(
	id NUMBER ,
	policy_id NUMBER,
	layout_id NUMBER,
	start_time VARCHAR2 (5),
	end_time VARCHAR2 (5),
	name VARCHAR2 (100),
	use_percent CHAR (1),
	width NUMBER,
	height NUMBER,
	lcomment VARCHAR2 (200),
	fullscreen CHAR (1),
	primary key ( id )
)
/

CREATE TABLE timed_list
(
	id NUMBER ,
	policy_id NUMBER,
	area_id NUMBER,
	type VARCHAR2 (2),
	start_time VARCHAR2 (5),
	end_time VARCHAR2 (5),
	loop CHAR (1),
	content NVARCHAR2 (1000),
	primary key ( id )
)
/

CREATE TABLE uploading_videos
(
	id NUMBER ,
	code VARCHAR2 (64),
	file_name NVARCHAR2 (100),
	origin_name NVARCHAR2 (100),
	current_size NUMBER,
	expected_size NUMBER,
	last_modified_time DATE,
	uploaded CHAR (1),
	primary key ( id )
)
/

CREATE TABLE man_user_role
(
	username NVARCHAR2 (30),
	role_id NUMBER,
	primary key (		
			username,
			role_id	)
)
/

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

CREATE TABLE videos
(
	code VARCHAR2 (64),
	origin_name NVARCHAR2 (100),
	file_name NVARCHAR2 (100),
	name NVARCHAR2 (100),
	tag NVARCHAR2 (100),
	description NVARCHAR2 (300),
	status VARCHAR2 (50),
	live_time_start DATE,
	live_time_end DATE,
	submitter NVARCHAR2 (50),
	submit_at DATE,
	auditor NVARCHAR2 (50),
	audit_at DATE,
	width NUMBER,
	height NUMBER,
	play_time NUMBER,
	discrim NVARCHAR2 (50),
	media_type NVARCHAR2 (20),
	media_size NUMBER,
	primary key ( code )
)
/

CREATE TABLE video_category
(
	video_code VARCHAR2 (64),
	category_id NUMBER,
	primary key (		
			video_code,
			category_id	)
)
/

