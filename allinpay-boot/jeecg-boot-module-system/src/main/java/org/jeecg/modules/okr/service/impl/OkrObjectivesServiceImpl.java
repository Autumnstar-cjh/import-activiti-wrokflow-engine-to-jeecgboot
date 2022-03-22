package org.jeecg.modules.okr.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.okr.entity.OkrObjectives;
import org.jeecg.modules.okr.entity.OkrProgress;
import org.jeecg.modules.okr.mapper.OkrObjectivesMapper;
import org.jeecg.modules.okr.service.IOkrObjectivesService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: OKR目标
 * @Author: jeecg-boot
 * @Date:   2019-12-03
 * @Version: V1.0
 */
@Service
public class OkrObjectivesServiceImpl extends ServiceImpl<OkrObjectivesMapper, OkrObjectives> implements IOkrObjectivesService {

	@Override
	public void addOkrObjectives(OkrObjectives okrObjectives) {
		if(oConvertUtils.isEmpty(okrObjectives.getParentId())){
			okrObjectives.setParentId(IOkrObjectivesService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			OkrObjectives parent = baseMapper.selectById(okrObjectives.getParentId());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(okrObjectives);
	}
	
	@Override
	public void updateOkrObjectives(OkrObjectives okrObjectives) {
		OkrObjectives entity = this.getById(okrObjectives.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getParentId();
		String new_pid = okrObjectives.getParentId();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				okrObjectives.setParentId(IOkrObjectivesService.ROOT_PID_VALUE);
			}
			if(!IOkrObjectivesService.ROOT_PID_VALUE.equals(okrObjectives.getParentId())) {
				baseMapper.updateTreeNodeStatus(okrObjectives.getParentId(), IOkrObjectivesService.HASCHILD);
			}
		}
		baseMapper.updateById(okrObjectives);
	}
	
	@Override
	public void deleteOkrObjectives(String id) throws JeecgBootException {
		OkrObjectives okrObjectives = this.getById(id);
		if(okrObjectives==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(okrObjectives.getParentId());
		baseMapper.deleteById(id);
	}

	@Override
	public Map<String,Object> getPercent() {
        //时间参数 今年1月1日至今天
        Calendar cal =Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//今年年份
        String startTime = year+"0101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String endTime = sdf.format(date);

        OkrProgress op = baseMapper.getPercent(startTime,endTime);

        //返回到前端的日期要重新换一下格式
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        startTime = year+"-01-01";
        endTime = sdf.format(date);
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("okrProgress",op);
		return map;

	}


	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IOkrObjectivesService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<OkrObjectives>().eq("parent_id", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IOkrObjectivesService.NOCHILD);
			}
		}
	}

}
