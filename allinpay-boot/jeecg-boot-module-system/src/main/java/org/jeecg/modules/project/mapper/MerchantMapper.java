package org.jeecg.modules.project.mapper;

import java.util.List;
import org.jeecg.modules.project.entity.Merchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商户信息
 * @Author: jeecg-boot
 * @Date:   2019-12-25
 * @Version: V1.0
 */
public interface MerchantMapper extends BaseMapper<Merchant> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<Merchant> selectByMainId(@Param("mainId") String mainId);

	public Merchant queryByCode(@Param("code") String code);

	public List<String> getIdsByCodes(@Param("codes") List<String> codes);
}
