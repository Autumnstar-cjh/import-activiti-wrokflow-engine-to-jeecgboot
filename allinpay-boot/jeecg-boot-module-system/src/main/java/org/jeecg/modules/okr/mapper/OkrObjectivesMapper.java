package org.jeecg.modules.okr.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.okr.entity.OkrObjectives;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.okr.entity.OkrProgress;

import java.math.BigDecimal;

/**
 * @Description: OKR目标
 * @Author: jeecg-boot
 * @Date:   2019-12-03
 * @Version: V1.0
 */
public interface OkrObjectivesMapper extends BaseMapper<OkrObjectives> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

	/**
	 *
	 * @return
	 */
	public OkrProgress getPercent(@Param("startTime")String startTime,@Param("endTime") String endTime);

}
