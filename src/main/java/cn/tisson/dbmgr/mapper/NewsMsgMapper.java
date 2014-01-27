package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.NewsMsg;

import java.util.List;

import org.springframework.stereotype.Repository;

public interface NewsMsgMapper extends SuperMapper<NewsMsg> {
    List<NewsMsg> getAll();
}