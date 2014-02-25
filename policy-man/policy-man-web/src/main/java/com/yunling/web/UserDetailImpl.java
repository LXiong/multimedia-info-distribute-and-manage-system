package com.yunling.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class UserDetailImpl extends JdbcDaoImpl {

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                String dispName = rs.getString(4);
                return new SecurityUser(username, password, dispName, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }

        });
	}

	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }
        
        getJdbcTemplate().execute("update man_users set last_login=sysdate where username='"+username+"'");

        return new SecurityUser(returnUsername, userFromUserQuery.getPassword(), ((SecurityUser)userFromUserQuery).getDispName(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities);
	}
	
	@Override
	protected void addCustomAuthorities(String username,
			List<GrantedAuthority> authorities) {
		super.addCustomAuthorities(username, authorities);
		GrantedAuthority ga = new GrantedAuthorityImpl("loginedUser");
		authorities.add(ga);
	}
}
