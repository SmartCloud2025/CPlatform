package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.FansGroupMapper;
import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.model.FansGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author Jasic
 * Date 14-1-26.
 */
@Service
public class FansGroupService extends BaseService<FansGroup> {

    @Resource
    private FansGroupMapper mapper;

    public FansGroup selectByWebchaNamePriority(FansGroup fansGroupPara){
        return mapper.selectByWebchaNamePriority(fansGroupPara);
    }

    @Override
    public SuperMapper<FansGroup> getMapper() {
        return mapper;
    }
}
