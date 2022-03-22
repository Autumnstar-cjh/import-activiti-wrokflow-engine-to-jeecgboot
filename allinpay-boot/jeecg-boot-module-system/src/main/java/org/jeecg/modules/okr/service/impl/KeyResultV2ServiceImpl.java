package org.jeecg.modules.okr.service.impl;

import org.jeecg.modules.okr.entity.KeyResultV2;
import org.jeecg.modules.okr.mapper.KeyResultV2Mapper;
import org.jeecg.modules.okr.service.IKeyResultV2Service;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 关键指标-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Service
public class KeyResultV2ServiceImpl extends ServiceImpl<KeyResultV2Mapper, KeyResultV2> implements IKeyResultV2Service {
	
	@Autowired
	private KeyResultV2Mapper keyResultV2Mapper;
	
	@Override
	public List<KeyResultV2> selectByMainId(String mainId) {
		return keyResultV2Mapper.selectByMainId(mainId);
	}
}
