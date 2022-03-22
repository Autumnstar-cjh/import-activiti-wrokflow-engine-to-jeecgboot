package org.jeecg.modules.tourism.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.tourism.entity.TourismMerchants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 旅游商户表
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
public interface TourismMerchantsMapper extends BaseMapper<TourismMerchants> {
    public List<String> queryByIds(@Param("ids") String ids);

    public void addToMerchant(List<Merchant> merchants);
}
