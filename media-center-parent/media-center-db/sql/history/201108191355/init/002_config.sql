begin
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'AuthenIp', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'AuthenPort', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'onlineReport_interval', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'perfStatistics_interval', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'authenticate_interval', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'ftp_username', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'ftp_password', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'max_ftp_try_time', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'max_download_thread', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'log_upload_ip', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'log_upload_username', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'log_upload_password', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'media_ftp_ip', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'policy_ftp_ip', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'log_upload_time', '');
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'vpn_flag', '');
-- volume property added by L.J. on 7/18/2011
insert into CONFIG values(CONFIG_SEQ.nextval, -1, 'volume', '');
end;