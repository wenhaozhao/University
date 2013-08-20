package org.iti.control.data;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.iti.common.util.JsonUtil;
import org.iti.service.HelloWorldService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorld {
	@Resource(name = "helloWorldService")
	private HelloWorldService helloWorldService;

	@RequestMapping(value = "/data/helloWorld", method = RequestMethod.GET)
	@ResponseBody
	public String helloWorld() {
		List<Map<String, Object>> results = helloWorldService.helloWorld();
		String json = JsonUtil.toJson(results);
		return json;
	}
}
