package org.jeecg.modules.project.service.impl;

import org.jeecg.modules.project.entity.ProjectInfo;
import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.project.mapper.MerchantMapper;
import org.jeecg.modules.project.mapper.ProjectInfoMapper;
import org.jeecg.modules.project.service.IProjectInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 项目信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
@Service
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements IProjectInfoService {

	@Autowired
	private ProjectInfoMapper projectInfoMapper;
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Override
	@Transactional
	public void saveMain(ProjectInfo projectInfo, List<Merchant> merchantList) {
		projectInfoMapper.insert(projectInfo);
		if(merchantList!=null && merchantList.size()>0) {
			for(Merchant entity:merchantList) {
				//外键设置
				entity.setProjectId(projectInfo.getId());
				merchantMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ProjectInfo projectInfo,List<Merchant> merchantList) {
		projectInfoMapper.updateById(projectInfo);
		
		//1.先删除子表数据
		merchantMapper.deleteByMainId(projectInfo.getId());
		
		//2.子表数据重新插入
		if(merchantList!=null && merchantList.size()>0) {
			for(Merchant entity:merchantList) {
				//外键设置
				entity.setProjectId(projectInfo.getId());
				merchantMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		merchantMapper.deleteByMainId(id);
		projectInfoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			merchantMapper.deleteByMainId(id.toString());
			projectInfoMapper.deleteById(id);
		}
	}
	
}
