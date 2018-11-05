package bdqn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bdqn.pojo.User;

/**
 * 登陆拦截器
 *
 * @author Panda Baby
 * @date 2018-10-27
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 登陆检查
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getSession().getAttribute("loginUser");
		if (null != user) {

		} else {
			request.setAttribute("msg", "抱歉,请先登录!");
			response.sendRedirect("/SpringBoot");
			return false;
		}
		return true;
	}
}
