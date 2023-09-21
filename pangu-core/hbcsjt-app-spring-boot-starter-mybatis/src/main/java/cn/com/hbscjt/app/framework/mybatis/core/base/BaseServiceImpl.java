package cn.com.hbscjt.app.framework.mybatis.core.base;

import cn.com.hbscjt.app.framework.common.constant.SystemConstant;
import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDO> extends ServiceImpl<M, T> implements BaseService<T> {

	@Override
	public boolean save(T entity) {
		LoginUser user = WebFrameworkUtils.getLoginUser();
		if (user != null) {
			entity.setCreator(user.getUserId());
			entity.setUpdater(user.getUserId());
		}
		Date now = DateUtil.date();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		if (entity.getStatus() == null) {
			entity.setStatus(SystemConstant.DB_STATUS_NORMAL);
		}
		entity.setDeleted(SystemConstant.DB_NOT_DELETED);
		return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
        LoginUser user = WebFrameworkUtils.getLoginUser();
		if (user != null) {
			entity.setUpdater(user.getUserId());
		}
		entity.setUpdateTime(DateUtil.date());
		return super.updateById(entity);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		return super.removeByIds(ids);
	}

}
