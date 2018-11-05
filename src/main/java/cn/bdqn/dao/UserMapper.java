package cn.bdqn.dao;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.pojo.User;

/**
 * 用户的Dao
 *
 * @author 毛俐鹏；
 * @date 2018-11-18;
 */
public interface UserMapper {
	/** 用户注册 */
	Integer addUser(User user);

	/** 删除用户信息 */
	Integer delUser(int u_id);

	/** 删除用户表中该角色下所用用户 */
	Integer deleteByU_role(int u_role);

	/** 用户修改信息 */
	Integer updateUser(User user);

	/** 查询一个用户 */
	User selectUser(User user);

	/** 根据id查询一个用户 */
	User selectUserById(@Param("u_id") Integer u_id);

	/** 用户登录 */
	User selectLogin(User user);
}
