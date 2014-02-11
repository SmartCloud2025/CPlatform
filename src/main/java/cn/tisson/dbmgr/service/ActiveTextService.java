package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveTextMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveText;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ActiveTextService extends BaseService<ActiveText> {

    @Resource
    private ActiveTextMapper mapper;


    @Override
    public SuperMapper<ActiveText> getMapper() {

        return mapper;
    }
}
