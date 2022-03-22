package org.jeecg.modules.project.service;

import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.project.entity.ProjectInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
public interface IProjectInfoService extends IService<ProjectInfo> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(ProjectInfo projectInfo, List<Merchant> merchantList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ProjectInfo projectInfo, List<Merchant> merchantList);
	
	/**
	 * 删除一对多
	 */
	public void delMain(String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);
	
}
