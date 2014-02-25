/** This file is SQL to create database table */
create table db_mc_version
(version varchar2(30))
/
/**
 * User and authority tables begin
 */
create table users
(
userid number primary key,
loginname varchar(30),
username varchar(30),
password varchar(30), 
telephone number,
email varchar(50),
last_logintime Date
)
/
CREATE SEQUENCE user_id
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10
/

create table roles (
	roleid number primary key,
	rolename varchar(30),
    describe varchar(50)
)
/
CREATE SEQUENCE role_id
     INCREMENT BY 1
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE
     CACHE 10
/

create table user_role
(
userId number references users(userid),
roleId number references roles(roleid),
primary key (userId, roleId)
)
/

create table role_function
(
roleId number references roles(roleid),
funcId varchar(50),
primary key (roleId,funcId)
)
/

/**
 * User and authority tables end
 */


/**
 * Province, city and district tables begin
 */
CREATE table "PROVINCE" (
    "PROVINCE_ID"   VARCHAR2(20) NOT NULL,
    "PROVINCE_NAME" VARCHAR2(50) NOT NULL,
    constraint  "PROVINCE_PK" primary key ("PROVINCE_ID")
)
/
CREATE table "CITY" (
    "CITY_ID"     VARCHAR2(20) NOT NULL,
    "CITY_NAME"   VARCHAR2(50) NOT NULL,
    "PROVINCE_ID" VARCHAR2(20) NOT NULL,
    constraint  "CITY_PK" primary key ("CITY_ID")
)
/
CREATE table "DISTRICT" (
    "DISTRICT_ID"   VARCHAR2(20) NOT NULL,
    "DISTRICT_NAME" VARCHAR2(50) NOT NULL,
    "CITY_ID"       VARCHAR2(20) NOT NULL,
    constraint  "DISTRICT_PK" primary key ("DISTRICT_ID")
)
/

/**
 * Province, city and distrinct tables end
 */

/**
 * Box logical group tables begin
 */
CREATE table "GROUPTYPE" (
    "TYPE_ID"          NUMBER NOT NULL,
    "TYPE_NAME"        VARCHAR2(50) NOT NULL,
    "TYPE_DESCRIPTION" VARCHAR2(200),
    constraint  "GROUPTYPE_PK" primary key ("TYPE_ID")
)
/
CREATE sequence "GROUPTYPE_SEQ" 
/ 

CREATE table "GROUPS" (
    "GROUP_ID"          NUMBER NOT NULL,
    "GROUP_NAME"        VARCHAR2(50) NOT NULL,
    "TYPE_ID"           NUMBER NOT NULL,
    "GROUP_DESCRIPTION" VARCHAR2(200),
    "CONF_ID"			NUMBER,
    "PACKAGEID"         NUMBER NULL,
    constraint  "GROUPS_PK" primary key ("GROUP_ID")
)
/
CREATE sequence "GROUPS_SEQ" 
/
/**
 * Box logical group tables end
 */

/**
 * Stb data tables begin
 */
CREATE table "STB" (
    "STB_MAC"            VARCHAR2(12) NOT NULL,
    "PROVINCE_ID"        VARCHAR2(20),
    "CITY_ID"            VARCHAR2(20),
    "DISTRICT_ID"        VARCHAR2(20),
    "ADDR_DETAIL"        VARCHAR2(100),
    "GROUP_ID"           NUMBER,
    "STB_TOKEN"          VARCHAR2(20),
    "CUSTOMER_NAME"      VARCHAR2(50),
    "STB_STATUS"         VARCHAR2(20),
    "STATUS_UPDATE_TIME" DATE,
    "STB_ID"             VARCHAR2(20) default '',
    "STB_DISABLED"       CHAR(1) default 'f',
    "CONF_ID" 			 NUMBER, 
    "POLICY_ID" 		 NUMBER, 
    "ACTIVE_POLICY" 	 VARCHAR2(30), 
    "ACTIVE_POLICY_SUCCESS_NUM" NUMBER, 
    "ACTIVE_POLICY_FAILED_NUM" NUMBER, 
    "PACKAGEID" 		 NUMBER, 
    "LAST_OFFLINE_TIME"  DATE, 
    "TYPEID" NUMBER  default -1, 
    "SHOPNO" VARCHAR2(50) NULL,
    "SHOPNAME" VARCHAR2(30) NULL,
    "TERMINALIP" VARCHAR2(30) NULL,
    "CONTACTS" VARCHAR2(50) NULL,
    "PHONE" VARCHAR2(30) NULL,
    "LAST_ACCESS_TIME" DATE NULL,
    constraint  "STB_PK" primary key ("STB_MAC")
)
/
CREATE TABLE "USER_STB"(
	"USERID"    NUMBER NOT NULL,  
    "TYPEFLAG"  NUMBER NULL, 
    "TYPEID"    NUMBER NOT NULL,
    CONSTRAINT "USER_STB_PK" PRIMARY KEY ("USERID", "TYPEFLAG", "TYPEID")
)
/
/**
 * Stb data tables end
 */

/**
 * Stb configuration tables begin
 */
CREATE table "CONF" (
    "CONF_ID"      NUMBER NOT NULL,
    "CONF_NAME"    VARCHAR2(20) NOT NULL,
    "CONF_VERSION" VARCHAR2(32) NOT NULL,
    "CREATE_TIME"  DATE NOT NULL,
    "UPDATE_TIME" DATE NOT NULL,
    constraint  "CONF_PK" primary key ("CONF_ID")
)
/
CREATE sequence "CONF_SEQ" 
/

CREATE TABLE  "CONFIG" (
	"CONFIG_ID" NUMBER NOT NULL, 
	"CONF_ID" NUMBER, 
	"CONFIG_KEY" VARCHAR2(50) NOT NULL, 
	"CONFIG_VALUE" VARCHAR2(100), 
	CONSTRAINT "CONFIG_PK" PRIMARY KEY ("CONFIG_ID")
)
/
CREATE sequence "CONFIG_SEQ"  
/
/**
 * Stb configuration tables end
 */

/**
 * Stb software tables begin
 */

create table boxpackage(
id number primary key,
name varchar(50),
description varchar(200),
updateat date
)
/
CREATE SEQUENCE boxpackage_id
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10
/
create table modulefile(
	id number primary key,
	module varchar(50),
	filepath varchar(100),
	version varchar(20),
	file_comment varchar(100),
	releasetime Date,
	verflycode varchar(50)
)
/
CREATE SEQUENCE module_id
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10  
/
CREATE TABLE packagefiles(
	packageid number references boxpackage(id),
	module varchar(50),
	fileid number references modulefile(id),
	primary key(packageid,module)
)
/

CREATE TABLE "STB_SOFT_VERSION"(
    "MAC"     VARCHAR2(12) NOT NULL,
    "TYPE"    VARCHAR2(30) NOT NULL,
    "VERSION" VARCHAR2(30),
    CONSTRAINT "STB_SOFT_VERSION_PK" PRIMARY KEY ("MAC", "TYPE")
)
/
/**
 * Stb software tables end
 */

/**
 * Tables for server log begin
 */
create table "AUTH_WATCH_LOG"(
	"ID" number not null,
	"WATCH_IP" varchar2(50) not null,
	"WATCH_PORT" varchar2(50),
	"STB_IP" varchar2(50),
	"STB_PORT" varchar2(50) not null,
	"STB_MAC" varchar2(50),
	"CREATED_AT" date not null,
	constraint "AUTH_WATCH_LOG_KEY" primary key ("ID")
)
/
create sequence "AUTH_WATCH_LOG_ID"
/
create table "AUTHENTICATE_RECORDS"(
	"ID" number not null,
	"STB_IP" varchar2(50) not null,
	"STB_MAC" varchar2(50),
	"CUSTOMER_NAME" varchar2(50),
	"AUTH_STATUS" varchar2(50) not null,
	"WATCH_SERVER_IP" varchar2(50),
	"WATCH_SERVER_PORT" varchar2(20),
	"CREATED_AT" date not null,
	constraint "AUTHENTICATE_RECORDS_ID" primary key ("ID")
)
/
create sequence "AUTH_RECORD_ID" 
/
CREATE TABLE CONFIG_DOWNLOAD_LOG
(
    USERID       NUMBER ,
    DOWNLOADITEM VARCHAR2(50 BYTE),
    DOWNLOADTIME DATE 
)
/
create table loginlog(
  logintime Date,
  remoteip varchar(100),
  issuccess char(1), 
  loginid number,
  loginname varchar(30)
)
/
create table operationlog(
 operationtime Date, 
 Currentuserid number,
 action varchar(20),
 updateobject varchar(40),
 objectid number,
 issuccess char(1)
)
/
CREATE table "STB_OPERATION_LOG" (
    "LOGID"           NUMBER NOT NULL,
    "MAC"			VARCHAR2(12) NOT NULL,
    "USERID"      NUMBER NOT NULL,
    "COMMAND"      VARCHAR2(50) NOT NULL,
    "RESULT"       VARCHAR2(200) NOT NULL,
    "RUNTIME"     DATE NOT NULL,
    constraint  "STB_OPERATION_LOG_PK" primary key ("LOGID")
)
/

CREATE sequence "STB_OPERATION_LOG_SEQ" 
/

create table "WATCH_COMMAND_LOG"(
	"ID" number not null,
	"WATCH_IP" varchar2(50) not null,
	"WATCH_PORT" varchar2(50),
	"STB_MAC" varchar2(50),
	"CUSTOMER_NAME" varchar2(50),
	"STB_IP" varchar2(50) not null,
	"COMMAND" varchar2(50),
	"CREATED_AT" date not null,
	constraint "WATCH_COMMAND_LOG_KEY" primary key ("ID")
)
/
create sequence "WATCH_COMMAND_LOG_ID" 
/
create table "WATCH_LOG"(
	"ID" number not null,
	"WATCH_IP" varchar2(50) not null,
	"WATCH_PORT" varchar2(50),
	"STB_IP" varchar2(50),
	"STB_PORT" varchar2(50) not null,
	"STB_MAC" varchar2(50),
	"TYPE" varchar2(20),
	"CREATED_AT" date not null,
	"STATUS" varchar2(50),
	constraint "WATCH_LOG_KEY" primary key ("ID")
)
/
create sequence "WATCH_LOG_ID"
/
/**
 * Server log end
 */

/**
 * Box log begin
 */
CREATE table BOXDOWNLOG (
	ID	NUMBER NOT NULL primary key, 
	MAC	VARCHAR2(12), 
	POLICYNUMBER	NUMBER, 
	VIDEONAME	VARCHAR2(100), 
	DONESIZE	NUMBER, 
	TOTALSIZE	NUMBER, 
	LOGTIME	DATE
)
/
CREATE sequence "BOXDOWNLOG_SEQ"
/
CREATE table BOXDOWNSTATUS (
	MAC	VARCHAR2(12), 
	POLICYNUMBER	NUMBER, 
	VIDEONAME	VARCHAR2(100), 
	DONESIZE	NUMBER, 
	TOTALSIZE	NUMBER, 
	STARTTIME	DATE, 
	ENDTIME	DATE,
	ISFINISHED	CHAR(1),
	PRIMARY KEY (MAC, POLICYNUMBER, VIDEONAME)
)
/

create table boxperflog(
	id number primary key,
	mac varchar2(12),
	logtime Date,
	memused number(2),
	diskused number(2),
	cupused number(2)
)
/
CREATE SEQUENCE seq_perflog
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10
/

create table boxplaylog(
id number primary key,
mac varchar2(12),
logtime Date,
videoname varchar2(100),
policynumber varchar(100)
)
/
CREATE SEQUENCE seq_playlog
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10
/
/**
 * Box log end
 */

-- Policy related begin
create table policy (
policyid number primary key,
policy_number number,
beginAt DATE,
endAt DATE,
createAt DATE,
file_path varchar(100),
md5 varchar(100),
size_bytes number,
update_time Date
)
/
CREATE SEQUENCE seq_policy
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10
/
create table video(
videoid number primary key,
readablename varchar(100),
name varchar(150),
plicyid references policy(policyid)
)
/
CREATE SEQUENCE seq_video
     INCREMENT BY 1   
     START WITH 1     
     NOMAXVALUE      
     NOCYCLE          
     CACHE 10
/
-- Policy related end
