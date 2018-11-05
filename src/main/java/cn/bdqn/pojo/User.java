package cn.bdqn.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.annotation.JSONField;

import cn.bdqn.util.FinalUtil;

/**
 * 用户类
 *
 * @author 毛俐鹏
 * @table base_user
 * @since jdk1.8.0_131
 * @date 2018-10-22
 */
@Component
@ConfigurationProperties(prefix = "user")
public class User implements Serializable {
	private static final long serialVersionUID = -3199224676087335152L;
	public static final String TABLE = "base_user";
	private Integer u_id; // id;
	private String u_uuid; // uuid;
	private String u_username; // 登录名;
	private String u_name; // 用户名;
	private String u_password; // 密码;
	private String u_email; // 邮箱;
	private Integer u_r_id; // 角色;
	private String u_images; // 头像路径;
	@JSONField(format = FinalUtil.DATE)
	private Date u_update_time;// 更新时间;
	private String u_remark; // 备注;
	private Boolean u_del_flag; // 删除标记;

	public Integer getU_id() {
		return u_id;
	}

	public void setU_id(Integer u_id) {
		this.u_id = u_id;
	}

	public String getU_uuid() {
		return u_uuid;
	}

	public void setU_uuid(String u_uuid) {
		this.u_uuid = u_uuid;
	}

	public String getU_username() {
		return u_username;
	}

	public void setU_username(String u_username) {
		this.u_username = u_username;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	public Integer getU_r_id() {
		return u_r_id;
	}

	public void setU_r_id(Integer u_r_id) {
		this.u_r_id = u_r_id;
	}

	public String getU_images() {
		return u_images;
	}

	public void setU_images(String u_images) {
		this.u_images = u_images;
	}

	public Date getU_update_time() {
		return u_update_time;
	}

	public void setU_update_time(Date u_update_time) {
		this.u_update_time = u_update_time;
	}

	public String getU_remark() {
		return u_remark;
	}

	public void setU_remark(String u_remark) {
		this.u_remark = u_remark;
	}

	public Boolean getU_del_flag() {
		return u_del_flag;
	}

	public void setU_del_flag(Boolean u_del_flag) {
		this.u_del_flag = u_del_flag;
	}

	public User() {
		super();
	}

	public User(String u_username, String u_password) {
		super();
		this.u_username = u_username;
		this.u_password = u_password;
	}

	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", u_uuid=" + u_uuid + ", u_username=" + u_username + ", u_name=" + u_name + ", u_password=" + u_password + ", u_email=" + u_email + ", u_r_id=" + u_r_id + ", u_images=" + u_images + ", u_remark=" + u_remark + ", u_update_time=" + u_update_time + ", u_del_flag=" + u_del_flag + "]";
	}
}
