package org.jeecg.modules.quality.service.impl;

import org.jeecg.modules.quality.entity.QualityMerchant;
import org.jeecg.modules.quality.mapper.QualityMerchantMapper;
import org.jeecg.modules.quality.service.IQualityMerchantService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 特色产业优质商户表
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Service
public class QualityMerchantServiceImpl extends ServiceImpl<QualityMerchantMapper, QualityMerchant> implements IQualityMerchantService {
	
	@Autowired
	private QualityMerchantMapper qualityMerchantMapper;
	
	@Override
	public List<QualityMerchant> selectByMainId(String mainId) {
		return qualityMerchantMapper.selectByMainId(mainId);
	}
}
