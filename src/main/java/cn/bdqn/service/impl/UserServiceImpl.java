package cn.bdqn.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.bdqn.dao.UserMapper;
import cn.bdqn.pojo.User;
import cn.bdqn.service.UserService;
import cn.bdqn.util.Excel;
import cn.bdqn.util.ExcelUtil;
import cn.bdqn.util.MD5Util;

/**
 * User的实现类<br>
 *
 * @author 毛俐鹏;
 * @date 2018-02-28;
 */
@Service
public class UserServiceImpl extends ServiceImpl implements UserService {

	@Autowired
	private final UserMapper userMapper = null;

	@Override
	public Integer add(Object bean) {
		page.getMap().remove(page.getPrimary());
		page.getMap().put("u_password", MD5Util.getMD5ofStr(page.getMap().get("u_password")));
		return mapper.add(page.getTable(), page.getMap());
	}

	@Override
	public Integer delUser(int u_id) {
		return userMapper.delUser(u_id);
	}

	@Override
	public Integer updateUser(User user) {
		user.setU_password(MD5Util.getMD5ofStr(user.getU_password()));
		return userMapper.updateUser(user);
	}

	@Override
	public User selectUser(User user) {
		return userMapper.selectUser(user);
	}

	@Override
	public User selectLogin(User user) {
		user.setU_password(MD5Util.getMD5ofStr(user.getU_password()));
		user = userMapper.selectLogin(user);
		if (null != user) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ul_ip", ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr());
			map.put("ul_u_id", user.getU_id().toString());
			map.put("ul_u_username", user.getU_username());
			page.setPrefix("ul");
			mapper.add("base_user_log", map);
		}
		return user;
	}

	@Override
	public void doSelectPage() {
		page.setTable(User.TABLE);
		page.setColumns("u_id,u_name,u_r_id,u_update_time");
	}

	@Override
	public List<Map<String, Object>> selectList(Map<String, String> map) {
		page.setTable(User.TABLE);
		page.setMap(map);
		page.setColumns("u_name,u_r_id,u_update_time");
		page.setRows(0);
		return mapper.selectList(page.getColumns(), page.getTable(), page.getMap(), page.getPrefix(), page.getOrder(), page.getMinPage(), page.getRows());
	}

	@Override
	public Integer importExcel(Map<String, Object> map) {
		int count = 0;
		page.setTable(User.TABLE);
		List<Map<String, String>> mapList = null;
		try {
			mapList = ExcelUtil.importExcel((InputStream) map.get("file"), "u_username,u_name,u_r_id,u_email");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 手动将数据转化为与数据库相对应的格式;
		String key = "u_r_id";
		if (!mapList.isEmpty())
			for (Map<String, String> map1 : mapList) {
				String value = map1.get(key);
				if ("房管".equals(value))
					map1.put(key, "1");
				else if ("会员".equals(value))
					map1.put(key, "2");
				// 逐条插入数据库；
				count += mapper.add(page.getTable(), map1);
			}
		return count;
	}

	@Override
	public Integer exportExcel(Map<String, String> map, String filePath, String fileName) {
		// 设置sql语句参数；
		page.setTable(User.TABLE);
		page.setMap(map);
		page.setRows(0);
		page.setColumns("u_name,u_r_id,u_update_time");

		/**
		 * 创建一个excel下载的对象；
		 */
		Excel excel = new cn.bdqn.util.Excel();
		excel.setTitle("用户表");
		excel.setColumns("用户名,角色,注册时间");
		excel.setFields(page.getColumns());
		excel.setExcelFile(new File(filePath));
		excel.setFileName(fileName);
		List<Map<String, Object>> mapList = mapper.selectList(page.getColumns(), page.getTable(), page.getMap(), page.getPrefix(), page.getOrder(), page.getMinPage(), page.getRows());
		excel.setMapList(mapList);
		return ExcelUtil.exportExcel(excel);
	}

	@Override
	public User selectUserById(Integer u_id) {
		return userMapper.selectUserById(u_id);
	}
}