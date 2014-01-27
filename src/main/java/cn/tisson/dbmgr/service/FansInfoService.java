package cn.tisson.dbmgr.service;


import cn.tisson.dbmgr.mapper.FansInfoMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.FansInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author Jasic
 * Date 14-1-20.
 */
@Service
public class FansInfoService extends BaseService<FansInfo> {

    @Resource
    private FansInfoMapper fansInfoMapper;

    public FansInfo selectByWebChatID(String webchatid) {
        return fansInfoMapper.selectByWebChatID(webchatid);
    }

    public int deleteByWebChatID(String webchatid) {
        return fansInfoMapper.deleteByWebChatID(webchatid);
    }


    @Override
    public SuperMapper<FansInfo> getMapper() {
        return fansInfoMapper;
    }


}
