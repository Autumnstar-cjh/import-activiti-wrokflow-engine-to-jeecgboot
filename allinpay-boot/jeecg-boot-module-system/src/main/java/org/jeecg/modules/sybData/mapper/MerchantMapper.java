package org.jeecg.modules.sybData.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.sybData.entity.Merchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.sybData.entity.MerchantQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 收银宝商户
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Repository("sybMerchantMapper")
public interface MerchantMapper extends BaseMapper<Merchant> {
    //查询商户结算信息
    public List<Merchant> queryMerchantBankInfo(@Param("merchant") MerchantQueryParam merchant);
}
