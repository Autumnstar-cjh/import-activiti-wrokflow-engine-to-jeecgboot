package org.jeecg.modules.okr.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.okr.entity.ObjectivesV2;
import org.jeecg.modules.okr.entity.KeyResultV2;
import org.jeecg.modules.okr.entity.UserRole;
import org.jeecg.modules.okr.mapper.KeyResultV2Mapper;
import org.jeecg.modules.okr.mapper.ObjectivesV2Mapper;
import org.jeecg.modules.okr.service.IObjectivesV2Service;
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
 * @Description: 工作目标表-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Service
public class ObjectivesV2ServiceImpl extends ServiceImpl<ObjectivesV2Mapper, ObjectivesV2> implements IObjectivesV2Service {

	@Autowired
	private ObjectivesV2Mapper objectivesV2Mapper;
	@Autowired
	private KeyResultV2Mapper keyResultV2Mapper;
	
	@Override
	@Transactional
	public void saveMain(ObjectivesV2 objectivesV2, List<KeyResultV2> keyResultV2List) {
		objectivesV2Mapper.insert(objectivesV2);
		if(keyResultV2List!=null && keyResultV2List.size()>0) {
			for(KeyResultV2 entity:keyResultV2List) {
				//外键设置
				entity.setObjId(objectivesV2.getId());
				keyResultV2Mapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ObjectivesV2 objectivesV2,List<KeyResultV2> keyResultV2List) {
		objectivesV2Mapper.updateById(objectivesV2);
		
		//1.先删除子表数据
		keyResultV2Mapper.deleteByMainId(objectivesV2.getId());
		
		//2.子表数据重新插入
		if(keyResultV2List!=null && keyResultV2List.size()>0) {
			for(KeyResultV2 entity:keyResultV2List) {
				//外键设置
				entity.setObjId(objectivesV2.getId());
				keyResultV2Mapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		keyResultV2Mapper.deleteByMainId(id);
		objectivesV2Mapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			keyResultV2Mapper.deleteByMainId(id.toString());
			objectivesV2Mapper.deleteById(id);
		}
	}

	@Override
	public SysDepart getDepartById(String id) {
		return objectivesV2Mapper.getDepartById(id);
	}

	@Override
	public Page<ObjectivesPage> getObjPage(Page<ObjectivesPage> page, Wrapper<ObjectivesPage> objectivesWrapper) {
		return page.setRecords(objectivesV2Mapper.getObjPage(page,objectivesWrapper));
	}

	@Override
	public UserRole queryAllPlanRole(String username) {
		return objectivesV2Mapper.queryAllPlanRole(username);
	}

}
