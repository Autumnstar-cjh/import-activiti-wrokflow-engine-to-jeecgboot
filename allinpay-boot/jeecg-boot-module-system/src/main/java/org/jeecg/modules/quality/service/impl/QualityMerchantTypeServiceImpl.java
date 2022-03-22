package org.jeecg.modules.quality.service.impl;

import org.jeecg.modules.quality.entity.QualityMerchantType;
import org.jeecg.modules.quality.entity.QualityMerchant;
import org.jeecg.modules.quality.mapper.QualityMerchantMapper;
import org.jeecg.modules.quality.mapper.QualityMerchantTypeMapper;
import org.jeecg.modules.quality.service.IQualityMerchantTypeService;
import org.jeecg.modules.quality.vo.QualityMerchantTypePage;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 特色产业优质客户商户类型
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Service
public class QualityMerchantTypeServiceImpl extends ServiceImpl<QualityMerchantTypeMapper, QualityMerchantType> implements IQualityMerchantTypeService {

	@Autowired
	private QualityMerchantTypeMapper qualityMerchantTypeMapper;
	@Autowired
	private QualityMerchantMapper qualityMerchantMapper;

	@Override
	public List<QualityMerchantTypePage> queryPage() {
		return qualityMerchantTypeMapper.queryPage();
	}

	@Override
	public List<QualityMerchantTypePage> queryPage(String type, String code) {
		return qualityMerchantTypeMapper.queryPageByParam(type,code);
	}

	@Override
	@Transactional
	public void saveMain(QualityMerchantType qualityMerchantType, List<QualityMerchant> qualityMerchantList) {
		qualityMerchantTypeMapper.insert(qualityMerchantType);
		if(qualityMerchantList!=null && qualityMerchantList.size()>0) {
			for(QualityMerchant entity:qualityMerchantList) {
				//外键设置
				entity.setTypeId(qualityMerchantType.getId());
				qualityMerchantMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(QualityMerchantType qualityMerchantType,List<QualityMerchant> qualityMerchantList) {
		qualityMerchantTypeMapper.updateById(qualityMerchantType);
		
		//1.先删除子表数据
		qualityMerchantMapper.deleteByMainId(qualityMerchantType.getId());
		
		//2.子表数据重新插入
		if(qualityMerchantList!=null && qualityMerchantList.size()>0) {
			for(QualityMerchant entity:qualityMerchantList) {
				//外键设置
				entity.setTypeId(qualityMerchantType.getId());
				qualityMerchantMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		qualityMerchantMapper.deleteByMainId(id);
		qualityMerchantTypeMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			qualityMerchantMapper.deleteByMainId(id.toString());
			qualityMerchantTypeMapper.deleteById(id);
		}
	}
	
}
