package org.jeecg.modules.project.service;

import org.jeecg.modules.project.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商户信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
public interface IMerchantService extends IService<Merchant> {

	public List<Merchant> selectByMainId(String mainId);

	public Merchant queryByCode(String code);

	public List<String> getIdsByCodes(List<String> codes);
}
