DROP SEQUENCE seq_man_roles
/
CREATE SEQUENCE seq_man_roles
/
DELETE FROM MAN_ROLE_AUTH
/
DELETE FROM MAN_ROLES
/
DELETE FROM MAN_USER_ROLE
/
DELETE FROM MAN_USERS
/


INSERT INTO MAN_USERS (USERNAME, PASSWORD, DISP_NAME, EMAIL, ENABLED) 
VALUES ('admin', 'admin', 'admin', 'a@a.com', '1');

INSERT INTO MAN_ROLES (ID, NAME, description)
VALUES (seq_man_roles.NEXTVAL, 'admin','管理员');

INSERT INTO man_user_role (username, role_id) VALUES('admin', seq_man_roles.CURRVAL);

insert into man_role_auth (role_id, authority_id) (select seq_man_roles.CURRVAL, id from mm_authority);

INSERT INTO MAN_ROLES (ID, NAME, description)
	VALUES (seq_man_roles.NEXTVAL, '策略编辑','策略编辑');
insert into man_role_auth (role_id, authority_id) 
	(select seq_man_roles.CURRVAL, id from mm_authority auth WHERE auth.name= 'AUTH_POLICY_LIST');
insert into man_role_auth (role_id, authority_id) 
	(select seq_man_roles.CURRVAL, id from mm_authority auth WHERE auth.name= 'AUTH_POLICY_EDIT');
insert into man_role_auth (role_id, authority_id) 
	(select seq_man_roles.CURRVAL, id from mm_authority auth WHERE auth.name= 'AUTH_POLICY_DELETE');
INSERT INTO MAN_USERS (USERNAME, PASSWORD, DISP_NAME, EMAIL, ENABLED) 
VALUES ('editor', 'editor', '策略编辑', 'a@a.com', '1');
INSERT INTO man_user_role (username, role_id) VALUES('editor', seq_man_roles.CURRVAL);
	
INSERT INTO MAN_ROLES (ID, NAME, description)
	VALUES (seq_man_roles.NEXTVAL, '策略审核者','策略审核者');
insert into man_role_auth (role_id, authority_id) 
	(select seq_man_roles.CURRVAL, id from mm_authority auth WHERE auth.name= 'AUTH_POLICY_AUDIT');
INSERT INTO MAN_USERS (USERNAME, PASSWORD, DISP_NAME, EMAIL, ENABLED) 
VALUES ('auditor', 'auditor', '策略审核', 'a@a.com', '1');
INSERT INTO man_user_role (username, role_id) VALUES('auditor', seq_man_roles.CURRVAL);
	
INSERT INTO MAN_ROLES (ID, NAME, description)
	VALUES (seq_man_roles.NEXTVAL, '策略发布者','策略发布者');
insert into man_role_auth (role_id, authority_id)
	(select seq_man_roles.CURRVAL, id from mm_authority auth WHERE auth.name= 'AUTH_POLICY_DEPLOY');
INSERT INTO MAN_USERS (USERNAME, PASSWORD, DISP_NAME, EMAIL, ENABLED) 
VALUES ('publisher', 'publisher', '策略发布人', 'a@a.com', '1');
INSERT INTO man_user_role (username, role_id) VALUES('publisher', seq_man_roles.CURRVAL);
