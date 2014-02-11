package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.CmdConfig;


import org.springframework.stereotype.Repository;

import java.util.List;

public interface CmdConfigMapper extends SuperMapper<CmdConfig> {


    List<CmdConfig> getAll();
    List<CmdConfig> getAllEffect();
}