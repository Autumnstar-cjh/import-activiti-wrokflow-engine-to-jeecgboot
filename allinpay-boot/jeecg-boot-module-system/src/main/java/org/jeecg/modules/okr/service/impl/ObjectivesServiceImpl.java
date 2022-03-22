package org.jeecg.modules.okr.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.okr.entity.Objectives;
import org.jeecg.modules.okr.entity.KeyResults;
import org.jeecg.modules.okr.entity.UserRole;
import org.jeecg.modules.okr.mapper.KeyResultsMapper;
import org.jeecg.modules.okr.mapper.ObjectivesMapper;
import org.jeecg.modules.okr.service.IObjectivesService;
import org.jeecg.modules.okr.vo.ObjectivesPage;
import org.jeecg.modules.system.entity.SysDepart;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: OKR目标表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Service
public class ObjectivesServiceImpl extends ServiceImpl<ObjectivesMapper, Objectives> implements IObjectivesService {

	@Autowired
	private ObjectivesMapper objectivesMapper;
	@Autowired
	private KeyResultsMapper keyResultsMapper;

	@Override
	@Transactional
	public void saveMain(Objectives objectives, List<KeyResults> keyResultsList) {
		objectivesMapper.insert(objectives);
		if(keyResultsList!=null && keyResultsList.size()>0) {
			for(KeyResults entity:keyResultsList) {
				//外键设置
				entity.setObjId(objectives.getId());
				keyResultsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Objectives objectives,List<KeyResults> keyResultsList) {
		objectivesMapper.updateById(objectives);
		
		//1.先删除子表数据
		keyResultsMapper.deleteByMainId(objectives.getId());
		
		//2.子表数据重新插入
		if(keyResultsList!=null && keyResultsList.size()>0) {
			for(KeyResults entity:keyResultsList) {
				//外键设置
				entity.setObjId(objectives.getId());
				keyResultsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		keyResultsMapper.deleteByMainId(id);
		objectivesMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			keyResultsMapper.deleteByMainId(id.toString());
			objectivesMapper.deleteById(id);
		}
	}

	@Override
	public SysDepart getDepartById(String id) {
		return objectivesMapper.getDepartById(id);
	}

	@Override
	public Page<ObjectivesPage> getObjPage(Page<ObjectivesPage> page,Wrapper<ObjectivesPage> objectivesWrapper){
		return page.setRecords(objectivesMapper.getObjPage(page,objectivesWrapper));
	}

	/**
	 * 查看所有工作目标的权限
	 * @return
	 */
	@Override
	public UserRole queryAllPlanRole(String username) {
//		System.out.println("service层==============================================================");
//		System.out.println("当前用户名："+username);
//		System.out.println("查询到的实体"+objectivesMapper.queryAllPlanRole(username));
		return objectivesMapper.queryAllPlanRole(username);
	}

}
