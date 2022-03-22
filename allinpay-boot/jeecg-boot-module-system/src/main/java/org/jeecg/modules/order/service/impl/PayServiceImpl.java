package org.jeecg.modules.order.service.impl;

import org.jeecg.modules.order.entity.Pay;
import org.jeecg.modules.order.mapper.PayMapper;
import org.jeecg.modules.order.service.IPayService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 支付表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements IPayService {
	
	@Autowired
	private PayMapper payMapper;
	
	@Override
	public List<Pay> selectByMainId(String mainId) {
		return payMapper.selectByMainId(mainId);
	}

	@Override
	public List<Pay> selectPays(String id) {
		return payMapper.selectPays(id);
	}
}
