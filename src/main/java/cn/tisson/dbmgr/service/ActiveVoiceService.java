package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveVoiceMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveVoice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActiveVoiceService extends BaseService<ActiveVoice> {

    @Resource
    private ActiveVoiceMapper mapper;

    @Override
    public SuperMapper<ActiveVoice> getMapper() {
        return mapper;
    }
}
