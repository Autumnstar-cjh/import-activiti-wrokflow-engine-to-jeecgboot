package org.jeecg.modules.okr.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.okr.entity.KeyResults;
import org.jeecg.modules.okr.entity.Objectives;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.okr.entity.UserRole;
import org.jeecg.modules.okr.vo.ObjectivesPage;
import org.jeecg.modules.system.entity.SysDepart;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: OKR目标表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
public interface IObjectivesService extends IService<Objectives> {


	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Objectives objectives,List<KeyResults> keyResultsList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Objectives objectives,List<KeyResults> keyResultsList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

	/**
	 * 通过id查询部门
	 * @param id
	 * @return
	 */
	public SysDepart getDepartById(String id);


	public Page<ObjectivesPage> getObjPage(Page<ObjectivesPage> page, Wrapper<ObjectivesPage> objectivesWrapper);

	/**
	 * 查看所有工作目标的权限
	 * @return
	 */
	public UserRole queryAllPlanRole(String username);
}
