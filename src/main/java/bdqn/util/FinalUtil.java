package bdqn.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bdqn.service.Service;

/**
 * 全局参数的配置
 *
 * @author 毛俐鹏;
 * @date 2018-04-13;
 */
public final class FinalUtil {
	/** 开发版本 */
	public static final String VERSION = "develop";

	/** 当前数据库版本 */
	public static final String SQL_VERSION = "mysql";

	/** 系统默认的实现类 */
	public static final String SERVICE_IMPL_ID = "ServiceImpl";

	/** 日期格式 */
	public static final String DATE = "yyyy-MM-dd";

	/** 时间格式 */
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

	/** excel下载路径 */
	public static final String EXCEL_PATH = "/statics/doc/excel";

	/** 未知异常或用户非法操作 */
	public static final String ERROR = "未知异常或用户非法操作!";

	/** 登陆异常提示 */
	public static final String LOGIN_EXCEPTION = "用户名或密码错误!";

	/** 入参不能为空 */
	public static final String PARAMS_IS_NULL_EXCEPTION = "入参不能为空!";

	/** 读取Spring配置信息 */
	public static final ApplicationContext CTX = new ClassPathXmlApplicationContext("applicationContext.xml");

	/** 初始化ServiceImpl */
	public static final Service SERVICE = (Service) FinalUtil.CTX.getBean(FinalUtil.SERVICE_IMPL_ID);
}
