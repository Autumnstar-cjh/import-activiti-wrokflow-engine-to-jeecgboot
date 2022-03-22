package org.jeecg.modules.okr.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.okr.entity.Objectives;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.okr.entity.UserRole;
import org.jeecg.modules.okr.vo.ObjectivesPage;
import org.jeecg.modules.system.entity.SysDepart;

/**
 * @Description: OKR目标表
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
public interface ObjectivesMapper extends BaseMapper<Objectives> {

    public SysDepart getDepartById(@Param("id") String id);

    public List<ObjectivesPage> getObjPage(Page<ObjectivesPage> page, @Param(Constants.WRAPPER) Wrapper<ObjectivesPage> objectivesWrapper);

    public UserRole queryAllPlanRole(@Param("username") String username);
}
