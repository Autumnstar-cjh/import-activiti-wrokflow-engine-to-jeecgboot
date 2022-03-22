package org.jeecg.modules.sybData.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.sybData.entity.Merchant;
import org.jeecg.modules.sybData.entity.MerchantQueryParam;
import org.jeecg.modules.sybData.mapper.MerchantMapper;
import org.jeecg.modules.sybData.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 收银宝商户
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Service("sybMerchantServiceImpl")
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Autowired
    MerchantMapper merchantMapper;

    @DS("multi-datasource1")//多数据源配置
    @Override
    public List<Merchant> queryMerchantBankInfo(MerchantQueryParam merchant) {

        if(merchant.getCreateDate() != null && !"".equals(merchant.getCreateDate())){
            String start = merchant.getCreateDate().split(",")[0];
            String end = merchant.getCreateDate().split(",")[1];

            merchant.setStartCreateDate(start);
            merchant.setEndCreateDate(end);
        }

        if(merchant.getChangeDate() != null && !"".equals(merchant.getChangeDate())){
            String start = merchant.getChangeDate().split(",")[0];
            String end = merchant.getChangeDate().split(",")[1];

            merchant.setStartChangeDate(start);
            merchant.setEndChangeDate(end);
        }

        return merchantMapper.queryMerchantBankInfo(merchant);
    }
}
