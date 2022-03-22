package org.jeecg.modules.okr.mapper;

import java.util.List;
import org.jeecg.modules.okr.entity.KeyResultV2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 关键指标-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
public interface KeyResultV2Mapper extends BaseMapper<KeyResultV2> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<KeyResultV2> selectByMainId(@Param("mainId") String mainId);
}
