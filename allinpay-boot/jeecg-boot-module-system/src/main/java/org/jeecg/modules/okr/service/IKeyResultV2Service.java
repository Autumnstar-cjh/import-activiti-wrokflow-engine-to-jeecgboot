package org.jeecg.modules.okr.service;

import org.jeecg.modules.okr.entity.KeyResultV2;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 关键指标-版本2
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
public interface IKeyResultV2Service extends IService<KeyResultV2> {

	public List<KeyResultV2> selectByMainId(String mainId);
}
