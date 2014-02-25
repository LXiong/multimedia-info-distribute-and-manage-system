alter table "CITY" add QUERY_WEATHER_ID varchar(30)
/
alter table "CITY" add CITY_NAME_PINYIN varchar(30)
/

DELETE FROM db_mc_version;
INSERT INTO db_mc_version values('201108041850');