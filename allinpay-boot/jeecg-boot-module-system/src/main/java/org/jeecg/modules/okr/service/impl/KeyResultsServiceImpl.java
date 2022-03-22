package org.jeecg.modules.okr.service.impl;

import org.jeecg.modules.okr.entity.KeyResults;
import org.jeecg.modules.okr.mapper.KeyResultsMapper;
import org.jeecg.modules.okr.service.IKeyResultsService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 关键结果
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
@Service
public class KeyResultsServiceImpl extends ServiceImpl<KeyResultsMapper, KeyResults> implements IKeyResultsService {
	
	@Autowired
	private KeyResultsMapper keyResultsMapper;
	
	@Override
	public List<KeyResults> selectByMainId(String mainId) {
		return keyResultsMapper.selectByMainId(mainId);
	}
}
