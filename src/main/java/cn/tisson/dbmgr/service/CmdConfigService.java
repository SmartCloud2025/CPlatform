package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.CmdConfigMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.CmdConfig;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Jasic
 * Date 14-1-30.
 */
@Service

public class CmdConfigService extends BaseService<CmdConfig> {

    @Resource
    private CmdConfigMapper mapper;


    public List<CmdConfig> getAll() {
        return mapper.getAll();
    }

    public List<CmdConfig> getAllEffect() {
        return mapper.getAllEffect();
    }

    @Override
    public SuperMapper<CmdConfig> getMapper() {

        return mapper;

    }
}
