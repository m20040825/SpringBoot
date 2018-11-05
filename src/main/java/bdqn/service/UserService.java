package bdqn.service;

import bdqn.pojo.User;

/**
 * 用户的Service
 *
 * @author Panda Baby
 * @date 2018-10-31
 */
public interface UserService extends Service {

	/**
	 * 删除用户
	 *
	 * @param u_id 用户id
	 */
	Integer delUser(int u_id);

	/**
	 * 修改用户
	 *
	 * @param user 用户信息
	 */
	Integer updateUser(User user);

	/**
	 * 用户登录
	 *
	 * @param user 登陆信息
	 */
	User selectLogin(User user);

	/**
	 * 查询一个用户
	 *
	 * @param user 用户信息
	 */
	User selectUser(User user);
}