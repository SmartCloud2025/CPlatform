package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveVideoMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveVideo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActiveVideoService extends BaseService<ActiveVideo> {

    @Resource
    private ActiveVideoMapper mapper;

    @Override
    public SuperMapper<ActiveVideo> getMapper() {

        return mapper;
    }
}
