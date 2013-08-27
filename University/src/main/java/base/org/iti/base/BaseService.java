package org.iti.base;

import org.springframework.jdbc.core.JdbcTemplate;

public interface BaseService {
	public JdbcTemplate jdbcTemplate();

	public void cache(String key,String json);
}
