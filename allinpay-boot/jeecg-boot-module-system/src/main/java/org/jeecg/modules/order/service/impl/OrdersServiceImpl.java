package org.jeecg.modules.order.service.impl;

import org.jeecg.modules.order.entity.Orders;
import org.jeecg.modules.order.entity.Pay;
import org.jeecg.modules.order.mapper.PayMapper;
import org.jeecg.modules.order.mapper.OrdersMapper;
import org.jeecg.modules.order.service.IOrdersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private PayMapper payMapper;
	
	@Override
	@Transactional
	public void saveMain(Orders orders, List<Pay> payList) {
		ordersMapper.insert(orders);
		if(payList!=null && payList.size()>0) {
			for(Pay entity:payList) {
				//外键设置
				entity.setOrderId(orders.getId());
				payMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Orders orders,List<Pay> payList) {
		ordersMapper.updateById(orders);
		
		//1.先删除子表数据
		payMapper.deleteByMainId(orders.getId());
		
		//2.子表数据重新插入
		if(payList!=null && payList.size()>0) {
			for(Pay entity:payList) {
				//外键设置
				entity.setOrderId(orders.getId());
				payMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		payMapper.deleteByMainId(id);
		ordersMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			payMapper.deleteByMainId(id.toString());
			ordersMapper.deleteById(id);
		}
	}
	
}
