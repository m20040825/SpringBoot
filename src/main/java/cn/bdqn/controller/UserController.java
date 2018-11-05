package cn.bdqn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bdqn.pojo.User;
import cn.bdqn.service.UserService;

/**
 * 登陆的Controller
 *
 * @author Panda Baby
 * @date 2018-11-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{u_id}")
	public User getUser(@PathVariable Integer u_id) {
		User user = userService.selectUserById(u_id);
		return user;
	}

}
