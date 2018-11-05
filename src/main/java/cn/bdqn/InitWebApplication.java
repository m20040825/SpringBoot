package cn.bdqn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动主程序
 *
 * @author Panda Baby
 * @date 2018-10-15
 */
@MapperScan("cn.bdqn.dao")
@SpringBootApplication
public class InitWebApplication {

	/**
	 * 启动主程序
	 */
	public static void main(final String[] args) {

		SpringApplication.run(InitWebApplication.class, args);
	}
}
