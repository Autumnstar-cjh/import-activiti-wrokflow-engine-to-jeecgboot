package org.jeecg.modules.quality.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.quality.entity.QualityMerchantType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.quality.vo.QualityMerchantTypePage;

/**
 * @Description: 特色产业优质客户商户类型
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
public interface QualityMerchantTypeMapper extends BaseMapper<QualityMerchantType> {

    /**
     * 分页列表查询
     */
    public List<QualityMerchantTypePage> queryPage();
    /**
     * 分页列表查询-带参数
     */
    public List<QualityMerchantTypePage> queryPageByParam(@Param("type") String type,@Param("code") String code);
}
