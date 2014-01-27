package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.CmdConfig;


import org.springframework.stereotype.Repository;

public interface CmdConfigMapper extends SuperMapper<CmdConfig> {
    int deleteByPrimaryKey(Integer id);

    int insert(CmdConfig record);

    int insertSelective(CmdConfig record);

    CmdConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmdConfig record);

    int updateByPrimaryKey(CmdConfig record);
}