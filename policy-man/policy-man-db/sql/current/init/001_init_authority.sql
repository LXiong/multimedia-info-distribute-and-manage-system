-- 初始化权限组
begin
	MM_ADD_AUTH_GROUP('user-man','用户管理');
	MM_ADD_AUTH_GROUP('layout-man','布局管理');
	MM_ADD_AUTH_GROUP('upload-man','上传管理');
	MM_ADD_AUTH_GROUP('media-man','媒体文件管理');
	MM_ADD_AUTH_GROUP('policy-man','播放策略管理');
end;

DELETE FROM mm_authority
/
--初始化权限
begin
	MM_ADD_AUTH('user-man','AUTH_USER_LIST', '用户列表');
	MM_ADD_AUTH('user-man','AUTH_USER_EDIT', '编辑用户');
	MM_ADD_AUTH('user-man','AUTH_USER_ROLE', '设定用户权限');
	MM_ADD_AUTH('user-man','AUTH_ROLE', '角色管理');
	MM_ADD_AUTH('layout-man','AUTH_LAYOUT_LIST', '布局列表');
	MM_ADD_AUTH('layout-man','AUTH_LAYOUT_EDIT', '编辑布局');
	MM_ADD_AUTH('layout-man','AUTH_LAYOUT_DELETE', '删除布局');
	MM_ADD_AUTH('upload-man','AUTH_UPLOAD_LIST', '查看上传列表');
	MM_ADD_AUTH('upload-man','AUTH_UPLOAD_EDIT', '修改上传文件属性');
	MM_ADD_AUTH('upload-man','AUTH_UPLOAD_IMPORT', '入库');
	MM_ADD_AUTH('media-man','AUTH_MEDIA_LIST', '媒体文件列表');
	MM_ADD_AUTH('media-man','AUTH_MEDIA_EDIT', '媒体文件修改');
	MM_ADD_AUTH('media-man','AUTH_MEDIA_DELETE', '媒体文件删除');
	MM_ADD_AUTH('policy-man','AUTH_POLICY_LIST', '播放策略列表');
	MM_ADD_AUTH('policy-man','AUTH_POLICY_EDIT', '播放策略修改');
	MM_ADD_AUTH('policy-man','AUTH_POLICY_DELETE', '播放策略删除');
	MM_ADD_AUTH('policy-man','AUTH_POLICY_AUDIT', '播放策略审核');
	MM_ADD_AUTH('policy-man','AUTH_POLICY_DEPLOY', '播放策略发布');
	
end;