package org.jeecg.modules.tourism.service.impl;

import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.tourism.entity.TourismMerchants;
import org.jeecg.modules.tourism.mapper.TourismMerchantsMapper;
import org.jeecg.modules.tourism.service.ITourismMerchantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 旅游商户表
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Service
public class TourismMerchantsServiceImpl extends ServiceImpl<TourismMerchantsMapper, TourismMerchants> implements ITourismMerchantsService {

    @Autowired
    TourismMerchantsMapper tourismMerchantsMapper;

    @Override
    public List<String> queryByIds(String ids) {
        return tourismMerchantsMapper.queryByIds(ids);
    }

    /**
     * 添加到商户表
     */
    @Override
    public void addToMerchant(List<Merchant> merchantList) {
        tourismMerchantsMapper.addToMerchant(merchantList);
    }
}
