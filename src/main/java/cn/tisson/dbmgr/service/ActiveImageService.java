package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveImageMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActiveImageService extends BaseService<ActiveImage> {

    @Resource
    private ActiveImageMapper mapper;

    @Override
    public SuperMapper<ActiveImage> getMapper() {

        return mapper;
    }
}
