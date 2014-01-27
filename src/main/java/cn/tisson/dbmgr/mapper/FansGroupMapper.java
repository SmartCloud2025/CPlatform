package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.FansGroup;

import org.springframework.stereotype.Repository;

public interface FansGroupMapper extends SuperMapper<FansGroup> {
     FansGroup selectByWebchaNamePriority(FansGroup fansGroupPara);
}