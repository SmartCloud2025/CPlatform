package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.ActiveArticleMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.ActiveArticle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActiveArticleService extends BaseService<ActiveArticle> {

    @Resource
    private ActiveArticleMapper mapper;

    public List<ActiveArticle> getListByNewsMsgID(Integer newMsgID) {
        return mapper.getListByNewsMsgID(newMsgID);
    }

    @Override
    public SuperMapper<ActiveArticle> getMapper() {
        return mapper;
    }
}
