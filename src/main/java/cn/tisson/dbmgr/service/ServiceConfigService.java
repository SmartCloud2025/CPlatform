package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ServiceConfigMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ServiceConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ServiceConfigService extends BaseService<ServiceConfig> {

    @Resource
    private ServiceConfigMapper mapper;

    public List<ServiceConfig> getAll() {
        return mapper.getAll();
    }

    @Override
    public SuperMapper<ServiceConfig> getMapper() {

        return mapper;
    }
}
