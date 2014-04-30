package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.ServiceInfo;

import java.util.List;


public interface ServiceInfoMapper extends SuperMapper<ServiceInfo> {
    List<ServiceInfo> getAll();

    ServiceInfo getByWebChatId(String webChatId);
}
