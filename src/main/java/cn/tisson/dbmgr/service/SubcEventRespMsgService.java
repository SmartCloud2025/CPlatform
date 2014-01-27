package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.SubcEventRespMsgMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.SubcEventRespMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Jasic
 * Date 14-1-25.
 */
@Service
public class SubcEventRespMsgService extends BaseService<SubcEventRespMsg> {

    @Resource
    private SubcEventRespMsgMapper msgMapper;

    public List<SubcEventRespMsg> getAll() {
        return msgMapper.getAll();
    }

    @Override
    public SuperMapper<SubcEventRespMsg> getMapper() {
        return msgMapper;
    }
}
