package cn.tisson.dbmgr.service;


import cn.tisson.dbmgr.mapper.MassPushMsgMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.MassPushMsg;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service

public class MassPushMsgService extends BaseService<MassPushMsg>{

    @Resource
    private MassPushMsgMapper mapper;

    public List<MassPushMsg> getAll(){
        return mapper.getAll();
    }

    public List<MassPushMsg> getAllEffect(){
        return mapper.getAllEffect();
    }


    @Override
    public SuperMapper<MassPushMsg> getMapper() {
        return mapper;
    }
}
