package org.iti.base;

import org.springframework.jdbc.core.JdbcTemplate;

public interface BaseService {
	public JdbcTemplate jdbcTemplate();
}
