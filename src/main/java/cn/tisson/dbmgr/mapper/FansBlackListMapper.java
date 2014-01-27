package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.FansBlackList;

import org.springframework.stereotype.Repository;

public interface FansBlackListMapper extends SuperMapper<FansBlackList> {
    int deleteByPrimaryKey(Integer id);

    int insert(FansBlackList record);

    int insertSelective(FansBlackList record);

    FansBlackList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FansBlackList record);

    int updateByPrimaryKeyWithBLOBs(FansBlackList record);

    int updateByPrimaryKey(FansBlackList record);
}