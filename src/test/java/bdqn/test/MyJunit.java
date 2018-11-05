package bdqn.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.bdqn.dao.UserMapper;
import cn.bdqn.pojo.User;
import cn.bdqn.service.UserService;
import cn.bdqn.service.impl.UserServiceImpl;

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