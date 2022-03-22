package org.jeecg.modules.plan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.plan.entity.WorkPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * @Description: 工作计划
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
public interface WorkPlanMapper extends BaseMapper<WorkPlan> {

    public List<WorkPlan> getWorkPlanList(Page<WorkPlan> page, @Param(Constants.WRAPPER) Wrapper<WorkPlan> workPlanWrapper);

    public String getRealName(@Param("username")String username);

    public IPage<WorkPlan> getAllPlan(Page<WorkPlan> page, @Param(Constants.WRAPPER) Wrapper workPlanWrapper);

    /**通过id查询目标内容*/
    public String getObjectivesById(@Param("id") String id);
}
