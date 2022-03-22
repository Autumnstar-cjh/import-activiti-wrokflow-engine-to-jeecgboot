package org.jeecg.modules.okr.service;

import org.jeecg.modules.okr.entity.OkrObjectives;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.okr.entity.OkrProgress;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Description: OKR目标
 * @Author: jeecg-boot
 * @Date:   2019-12-03
 * @Version: V1.0
 */
public interface IOkrObjectivesService extends IService<OkrObjectives> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addOkrObjectives(OkrObjectives okrObjectives);
	
	/**修改节点*/
	void updateOkrObjectives(OkrObjectives okrObjectives) throws JeecgBootException;
	
	/**删除节点*/
	void deleteOkrObjectives(String id) throws JeecgBootException;

	/**OKR完成度*/
	public Map<String,Object> getPercent();

}
