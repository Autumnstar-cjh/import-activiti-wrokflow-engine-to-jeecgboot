package org.jeecg.modules.order.service;

import org.jeecg.modules.order.entity.Pay;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 支付表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
public interface IPayService extends IService<Pay> {

	public List<Pay> selectByMainId(String mainId);

	public List<Pay> selectPays(String id);
}
