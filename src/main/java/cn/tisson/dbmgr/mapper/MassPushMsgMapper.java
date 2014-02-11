package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.MassPushMsg;

import java.util.List;

public interface MassPushMsgMapper extends SuperMapper<MassPushMsg> {
    List<MassPushMsg> getAll();

    List<MassPushMsg> getAllEffect();
}