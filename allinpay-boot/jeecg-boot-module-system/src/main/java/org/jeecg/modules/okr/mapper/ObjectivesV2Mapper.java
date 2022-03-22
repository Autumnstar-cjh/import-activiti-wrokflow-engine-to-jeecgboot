package org.jeecg.modules.okr.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.okr.entity.ObjectivesV2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.okr.entity.UserRole;
import org.jeecg.modules.okr.vo.ObjectivesPage;
import org.jeecg.modules.system.entity.SysDepart;

/**
 * @Description: 工作目标表-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
public interface ObjectivesV2Mapper extends BaseMapper<ObjectivesV2> {

    public SysDepart getDepartById(@Param("id") String id);

    public List<ObjectivesPage> getObjPage(Page<ObjectivesPage> page, @Param(Constants.WRAPPER) Wrapper<ObjectivesPage> objectivesWrapper);

    public UserRole queryAllPlanRole(@Param("username") String username);
}
