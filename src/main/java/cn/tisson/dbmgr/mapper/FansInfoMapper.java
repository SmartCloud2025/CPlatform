package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.FansInfo;


import org.springframework.stereotype.Repository;

public interface FansInfoMapper extends SuperMapper<FansInfo> {
    FansInfo selectByWebChatID(String webchatid);

    int deleteByWebChatID(String webchatid);
}