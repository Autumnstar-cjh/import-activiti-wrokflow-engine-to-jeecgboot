package org.jeecg.modules.order.mapper;

import java.util.List;
import org.jeecg.modules.order.entity.Pay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 支付表
 * @Author: jeecg-boot
 * @Date:   2020-06-22
 * @Version: V1.0
 */
public interface PayMapper extends BaseMapper<Pay> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<Pay> selectByMainId(@Param("mainId") String mainId);

	public List<Pay> selectPays(@Param("id") String id);
}
