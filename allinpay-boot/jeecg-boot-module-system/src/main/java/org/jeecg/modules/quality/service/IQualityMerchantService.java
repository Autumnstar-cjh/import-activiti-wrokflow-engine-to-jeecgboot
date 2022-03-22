package org.jeecg.modules.quality.service;

import org.jeecg.modules.quality.entity.QualityMerchant;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 特色产业优质商户表
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
public interface IQualityMerchantService extends IService<QualityMerchant> {

	public List<QualityMerchant> selectByMainId(String mainId);
}
