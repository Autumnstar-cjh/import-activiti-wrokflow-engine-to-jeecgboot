package org.jeecg.modules.okr.mapper;

import java.util.List;
import org.jeecg.modules.okr.entity.KeyResults;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 关键结果
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
public interface KeyResultsMapper extends BaseMapper<KeyResults> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<KeyResults> selectByMainId(@Param("mainId") String mainId);
}
