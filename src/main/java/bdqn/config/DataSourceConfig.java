package bdqn.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 重写数据源的配置
 *
 * @author Panda Baby
 * @date 2018-10-31
 */
@Configuration
public class DataSourceConfig {

	/**
	 * 加载数据源的初始化配置
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource initDataSource() {
		return new DruidDataSource();
	}

	/**
	 * 配置Druid监控
	 *
	 * @return
	 */
	@Bean
	public ServletRegistrationBean startViewServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		Map<String, String> params = new HashMap<>();
		params.put("loginUsername", "admin");
		params.put("loginPassword", "123456");
		params.put("allow", "");
		bean.setInitParameters(params);
		return bean;
	}

	/**
	 * 配置web监控的filter
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());
		Map<String, String> params = new HashMap<>();
		params.put("exclusions", "*.css,*.js,/druid/*");
		bean.setInitParameters(params);
		bean.setUrlPatterns(Arrays.asList("/*"));
		return bean;
	}

}
