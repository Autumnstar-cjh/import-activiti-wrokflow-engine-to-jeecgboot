package org.jeecg.modules.quality.service;

import org.jeecg.modules.quality.entity.QualityMerchant;
import org.jeecg.modules.quality.entity.QualityMerchantType;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.quality.vo.QualityMerchantTypePage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 特色产业优质客户商户类型
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
public interface IQualityMerchantTypeService extends IService<QualityMerchantType> {
	/**
	 * 分页列表查询
	 */
	public List<QualityMerchantTypePage> queryPage();
	/**
	 * 分页列表查询-带参数
	 */
	public List<QualityMerchantTypePage> queryPage(String type,String code);

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(QualityMerchantType qualityMerchantType,List<QualityMerchant> qualityMerchantList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(QualityMerchantType qualityMerchantType,List<QualityMerchant> qualityMerchantList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


	
}
