create table "STB_WARNING_INFO"(
	"ID" number,
	"STB_MAC" varchar2(20) not null,
	"WARNING_TYPE" varchar2(20) not null,
	"DETAILS" varchar2(250),
	"CREATED_AT" date not null,
	constraint "STB_WARNING_INFO_KEY" primary key("ID")
)
/
create sequence "STB_WARNING_INFO_ID"
/