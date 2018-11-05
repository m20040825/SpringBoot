package cn.bdqn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.bdqn.controller.Control;
import cn.bdqn.dao.Mapper;
import cn.bdqn.pojo.Page;
import cn.bdqn.pojo.User;
import cn.bdqn.service.Service;
import cn.bdqn.util.FinalUtil;
import cn.bdqn.util.ToolUtil;

/**
 * 通用Service实现类
 *
 * @author 毛俐鹏
 * @date 2018-11-01
 */
@org.springframework.stereotype.Service(FinalUtil.SERVICE_IMPL_ID)
public class ServiceImpl extends Control implements Service {
	@Autowired
	protected final Mapper mapper = null;
	protected static Logger logger = Logger.getLogger(ServiceImpl.class);

	@Override
	public Integer add(Object bean) {
		page.getMap().remove(page.getPrimary());
		return mapper.add(page.getTable(), page.getMap());
	}

	@Override
	public Integer del(int[] ids) {
		User user = new User();
		for (int id : ids) {
			user.setU_id(id);
			super.setPage(user);
			mapper.del(page.getTable(), page.getPrefix(), page.getMap());
		}
		return null;
	}

	@Override
	public Integer del(Object bean) {
		return mapper.del(page.getTable(), page.getPrefix(), page.getMap());
	}

	@Override
	public Integer update(Object bean) {
		String primaryKey = page.getPrimary();
		String primaryValue = page.getMap().get(primaryKey);
		page.getMap().remove(primaryKey);
		return mapper.update(page.getTable(), primaryKey, primaryValue, page.getMap());
	}

	public void doSelectPage() {
		return;
	}

	@Override
	public Map<String, Object> selectPage(Page page) {
		this.page = page;
		this.doSelectPage();
		Integer total = this.page.getTotal();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rows = null;
		if (1 == this.page.getPage())
			total = mapper.selectTotal(this.page.getTable(), this.page.getPrefix(), this.page.getMap());
		rows = mapper.selectList(this.page.getColumns(), this.page.getTable(), this.page.getMap(), this.page.getPrefix(), this.page.getOrder(), this.page.getMinPage(), this.page.getRows());
		if (this.page.getPage() > 0)
			if (Boolean.TRUE == this.page.getIsOperate())
				for (Map<String, Object> map : rows)
					map.put("操作", "<a href='user_update.htm/" + map.get(page.getPrimary()) + "'>修改 </a>" + "<a href='javascript:void(0)' onclick='doDel(" + map.get(page.getPrimary()) + ")'>删除</a>");
		resultMap.put("rows", ToolUtil.toStrMapList(rows));
		if (0 != this.page.getPage())
			resultMap.put("total", total);
		else {
			// 下拉框；
		}
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> selectList(Object bean) {
		// page.setOrder("u_id DESC");
		List<Map<String, Object>> mapList = mapper.selectList(page.getColumns(), page.getTable(), page.getMap(), page.getPrefix(), page.getOrder(), page.getMinPage(), page.getRows());
		if (FinalUtil.VERSION == "develop")
			ToolUtil.sysoList(mapList);
		return mapList;
	}

	@Override
	public Integer add(Object[] beans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectOne(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectTotal(Object bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer importExcel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer exportExcel(Map<String, String> map, String filePath, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
