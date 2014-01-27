package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.SubcEventRespMsg;

import java.util.List;

import org.springframework.stereotype.Repository;

public interface SubcEventRespMsgMapper extends SuperMapper<SubcEventRespMsg> {
    List<SubcEventRespMsg> getAll();
}