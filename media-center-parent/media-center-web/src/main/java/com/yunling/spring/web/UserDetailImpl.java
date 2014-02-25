package com.yunling.spring.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
                SecurityUser securityUser = new SecurityUser(username, password, dispName, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
                securityUser.setId(rs.getLong(5));
				return securityUser;
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

        SecurityUser securityUser = new SecurityUser(returnUsername, userFromUserQuery.getPassword(), ((SecurityUser)userFromUserQuery).getDispName(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities);
        securityUser.setId(((SecurityUser)userFromUserQuery).getId());
		return securityUser;
	}
}
