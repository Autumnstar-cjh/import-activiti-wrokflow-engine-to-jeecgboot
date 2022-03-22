package org.jeecg.modules.sybData.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.sybData.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.sybData.entity.MerchantQueryParam;

import java.util.List;

/**
 * @Description: 收银宝商户
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
public interface MerchantService extends IService<Merchant> {

    //查询商户结算信息
    public List<Merchant> queryMerchantBankInfo(MerchantQueryParam merchant);

}
