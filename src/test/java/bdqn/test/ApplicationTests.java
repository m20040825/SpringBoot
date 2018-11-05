package bdqn.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.bdqn.dao.UserMapper;
import cn.bdqn.pojo.User;

/**
 * 测试类
 *
 * @author Panda Baby
 * @date 2018-11-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	private final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);
	@Autowired
	DataSource dataSource;
	@Autowired
	public UserMapper userMapper;

	@Test
	public void contextLoads() throws SQLException {

		System.out.println("=========spring boot test========");
		User user = new User("mlp", "792B20AEAA951D0CFA92004F53EB0D3B");

		logger.trace("tarce");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");

		user = userMapper.selectLogin(user);
		logger.info("user==" + user);
	}

}
