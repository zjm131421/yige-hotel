package com.yige.common.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.dao.LogDao;
import com.yige.common.domain.LogDO;
import com.yige.common.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @author zoujm
 * @since 2018/12/1 13:51
 */
@Service
public class LogServiceImpl extends CoreServiceImpl<LogDao,LogDO> implements LogService {

}
