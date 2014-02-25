package com.yunling.mediacenter.web.struts2;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.ConventionsServiceImpl;

import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.inject.Inject;

public class MyConventionsServiceImpl extends ConventionsServiceImpl {

	@Inject
    public MyConventionsServiceImpl(@Inject("struts.convention.result.path") String resultPath) {
		super(resultPath);
	}

	@Override
	public Map<String, ResultTypeConfig> getResultTypesByExtension(
			PackageConfig packageConfig) {
		Map<String, ResultTypeConfig> results = packageConfig.getAllResultTypeConfigs();

        Map<String, ResultTypeConfig> resultsByExtension = new HashMap<String, ResultTypeConfig>();
        resultsByExtension.put("jsp", results.get("layout"));
        resultsByExtension.put("jspx", results.get("layout"));
        resultsByExtension.put("vm", results.get("velocity"));
        resultsByExtension.put("ftl", results.get("freemarker"));
        resultsByExtension.put("html", results.get("dispatcher"));
        resultsByExtension.put("htm", results.get("dispatcher"));
        return resultsByExtension;
	}

}
