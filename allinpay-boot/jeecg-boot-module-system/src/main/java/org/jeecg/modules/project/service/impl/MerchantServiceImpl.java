package org.jeecg.modules.project.service.impl;

import org.jeecg.modules.project.entity.Merchant;
import org.jeecg.modules.project.mapper.MerchantMapper;
import org.jeecg.modules.project.service.IMerchantService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商户信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Override
	public List<Merchant> selectByMainId(String mainId) {
		return merchantMapper.selectByMainId(mainId);
	}

	@Override
	public Merchant queryByCode(String code) { return merchantMapper.queryByCode(code); }

	@Override
	public List<String> getIdsByCodes(List<String> codes) { return merchantMapper.getIdsByCodes(codes); }

}
