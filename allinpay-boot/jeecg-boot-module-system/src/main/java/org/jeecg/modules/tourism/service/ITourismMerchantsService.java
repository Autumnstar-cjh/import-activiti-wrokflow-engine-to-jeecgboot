package org.jeecg.modules.tourism.service;

import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.tourism.entity.TourismMerchants;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 旅游商户表
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
public interface ITourismMerchantsService extends IService<TourismMerchants> {
    public List<String> queryByIds(String ids);

    /** 添加到商户表 */
    public void addToMerchant(List<Merchant> merchantList);
}
