package org.iti.base.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.iti.base.BaseService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("baseService")
public class BaseServiceImpl implements BaseService {

	private JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public JdbcTemplate jdbcTemplate() {
		if (jdbcTemplate == null)
			throw new NullPointerException("JdbcTemplate is Null");
		return jdbcTemplate;
	}

}
