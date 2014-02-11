package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.ActiveArticle;

import java.util.List;

public interface ActiveArticleMapper extends SuperMapper<ActiveArticle> {

    List<ActiveArticle> getListByNewsMsgID(Integer newMsgID);

}