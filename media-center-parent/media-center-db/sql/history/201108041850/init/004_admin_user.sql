begin
INSERT INTO "USERS" (USERID, LOGINNAME, USERNAME, PASSWORD, TELEPHONE, EMAIL) 
VALUES ('1', 'admin', 'admin', 'admin', '1111', 'a@a.com');

insert into roles(roleid,rolename,describe) values (1,'site-admin','站点管理员');
insert into roles(roleid,rolename,describe) values (2,'customer-admin','客户管理员');

INSERT INTO USER_ROLE (USERID, ROLEID) VALUES ('1', '1');

insert into ROLE_FUNCTION VALUES(1,'CustomerDeviceEdit');
insert into ROLE_FUNCTION VALUES(1,'CustomerDeviceManage');
insert into ROLE_FUNCTION VALUES(1,'CustomerDeviceView');
insert into ROLE_FUNCTION VALUES(1,'CustomerRoleEdit');
insert into ROLE_FUNCTION VALUES(1,'CustomerUserAdmin');
insert into ROLE_FUNCTION VALUES(1,'DeviceLogView');
insert into ROLE_FUNCTION VALUES(1,'DeviceReport');
insert into ROLE_FUNCTION VALUES(1,'ServerLogView');
insert into ROLE_FUNCTION VALUES(1,'SiteCustomerAdmin');
insert into ROLE_FUNCTION VALUES(1,'SiteRoleAdmin');
insert into ROLE_FUNCTION VALUES(1,'SiteUserAdmin');
insert into ROLE_FUNCTION VALUES(1,'UpdateManage');
insert into ROLE_FUNCTION VALUES(1,'UsbDeploy');
end