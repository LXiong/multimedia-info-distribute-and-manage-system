begin
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(2, 'user-man', '管理用户');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(4, 'role-man', '管理角色');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(5, 'device-view', '查看设备');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(6, 'device-edit', '编辑设备信息');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(7, 'device-man', '管理设备');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(8, 'group-view', '查看分组');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(9, 'group-man', '管理分组');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(10, 'config-view', '查看配置');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(11, 'config-man', '管理配置');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(12, 'server-log', '查看服务器日志');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(13, 'device-log', '查看设备日志');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(14, 'usb-update', 'USB更新');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(15, 'policy-view', '查看策略文件');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(16, 'report-view', '查看报表');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(17, 'soft-view', '查看机顶盒软件');
	INSERT INTO mc_authority(id, name, locale_zh_cn)
	VALUES(18, 'soft-man', '管理机顶盒软件');
	
--	INSERT INTO mc_authority(id, name, locale_zh_cn)
--	VALUES(1, '', '');
end;