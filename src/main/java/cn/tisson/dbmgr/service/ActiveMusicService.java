package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveMusicMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveMusic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActiveMusicService extends BaseService<ActiveMusic> {

    @Resource
    private ActiveMusicMapper mapper;

    @Override
    public SuperMapper<ActiveMusic> getMapper() {


        return mapper;
    }
}
