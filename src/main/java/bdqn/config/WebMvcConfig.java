package bdqn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import bdqn.interceptor.LoginInterceptor;

/**
 * 重写spring mvc的默认配置
 *
 * @author Panda Baby
 * @date 2018-10-29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 自定义视图解析器
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/login");
		registry.addViewController("/main.html").setViewName("index");
	}

	/**
	 * 自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/user/login");
	}
}
