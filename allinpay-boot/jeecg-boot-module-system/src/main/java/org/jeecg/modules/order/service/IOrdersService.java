package org.jeecg.modules.order.service;

import org.jeecg.modules.order.entity.Pay;
import org.jeecg.modules.order.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
public interface IOrdersService extends IService<Orders> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Orders orders,List<Pay> payList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Orders orders,List<Pay> payList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
