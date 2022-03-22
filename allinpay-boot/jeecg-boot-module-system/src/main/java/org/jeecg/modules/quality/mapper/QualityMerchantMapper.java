package org.jeecg.modules.quality.mapper;

import java.util.List;
import org.jeecg.modules.quality.entity.QualityMerchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 特色产业优质商户表
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
public interface QualityMerchantMapper extends BaseMapper<QualityMerchant> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<QualityMerchant> selectByMainId(@Param("mainId") String mainId);
}
