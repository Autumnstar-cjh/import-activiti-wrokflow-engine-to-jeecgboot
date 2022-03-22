package org.jeecg.modules.okr.service;

import org.jeecg.modules.okr.entity.KeyResults;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 关键结果
 * @Author: jeecg-boot
 * @Date:   2020-03-26
 * @Version: V1.0
 */
public interface IKeyResultsService extends IService<KeyResults> {

	public List<KeyResults> selectByMainId(String mainId);
}
