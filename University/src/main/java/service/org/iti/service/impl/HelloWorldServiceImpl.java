package org.iti.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.iti.base.BaseService;
import org.iti.service.HelloWorldService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service("helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService {

	@Resource(name = "baseService")
	private BaseService baseService;

	@Override
	public List<Map<String, Object>> helloWorld() {
		List<Map<String, Object>> results = baseService.jdbcTemplate().query(
				"Select _ID,_Content from Test order by _ID asc",
				new RowMapper<Map<String, Object>>() {

					@Override
					public Map<String, Object> mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						Map<String, Object> result = new HashMap<String, Object>();
						result.put("_ID", arg0.getLong("_ID"));
						result.put("_Content", arg0.getString("_Content"));
						baseService.cache(Long.valueOf(arg0.getLong("_ID"))
								.toString(), arg0.getString("_Content"));
						return result;
					}
				});
		return results;
	}
}
