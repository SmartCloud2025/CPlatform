package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveNewsMsgMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveNewsMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActiveNewsService extends BaseService<ActiveNewsMsg> {

    @Resource
    private ActiveNewsMsgMapper mapper;

    @Override
    public SuperMapper<ActiveNewsMsg> getMapper() {

        return mapper;
    }
}
