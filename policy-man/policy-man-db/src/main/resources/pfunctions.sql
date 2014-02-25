DROP SEQUENCE seq_pfunctions
/
DROP TABLE pfunctions cascade constraints
/
CREATE TABLE pfunctions
(
	id NUMBER ,
	name VARCHAR2 (30),
	comment NVARCHAR2 (100),
	primary key ( id )
)
/
CREATE SEQUENCE seq_pfunctions
/
