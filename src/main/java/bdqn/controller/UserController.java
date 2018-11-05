package bdqn.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bdqn.pojo.User;
import bdqn.service.UserService;

/**
 * 用户的controller
 *
 * @author Panda Baby
 * @date 2018-11-02
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 访问主页
	 */
	@RequestMapping("index")
	public String index(Map<String, Object> map) {
		map.put("now", new Date());
		return "index";
	}

	/**
	 * 用户登录
	 *
	 * @param map
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("login")
	public String login(Map<String, Object> map, @RequestParam("u_username") String username, @RequestParam("u_password") String password, HttpSession session) {

		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			User user = new User();
			user.setU_username(username);
			user.setU_password(password);
			user = userService.selectLogin(user);
			if (user != null) {
				user.setU_username(username);
				session.setAttribute("loginUser", user);
				// 防止表单重复提交
				return "redirect:/main.html";
			} else {
				map.put("msg", "用户名或密码错误!");
				return "login";
			}
		}
		return "login";
	}
}
