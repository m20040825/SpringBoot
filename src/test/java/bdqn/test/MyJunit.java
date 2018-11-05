package bdqn.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import bdqn.dao.UserMapper;
import bdqn.pojo.User;
import bdqn.service.UserService;
import bdqn.service.impl.UserServiceImpl;

public class MyJunit {
	public UserService userService = new UserServiceImpl();
	@Autowired
	public UserMapper userMapper;

	@Test
	public void main() {
		User user = new User("mlp", "mlp");

		userMapper.selectLogin(user);
	}
}