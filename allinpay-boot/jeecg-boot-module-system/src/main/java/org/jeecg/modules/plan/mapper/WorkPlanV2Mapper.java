package org.jeecg.modules.plan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.plan.entity.WorkPlan;
import org.jeecg.modules.plan.entity.WorkPlanV2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 工作计划-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
public interface WorkPlanV2Mapper extends BaseMapper<WorkPlanV2> {

    public List<WorkPlanV2> getWorkPlanList(Page<WorkPlanV2> page, @Param(Constants.WRAPPER) Wrapper<WorkPlanV2> workPlanWrapper);

    public String getRealName(@Param("username")String username);

    public IPage<WorkPlanV2> getAllPlan(Page<WorkPlanV2> page, @Param(Constants.WRAPPER) Wrapper workPlanWrapper);

    /**通过id查询目标内容*/
    public String getObjectivesById(@Param("id") String id);
}
