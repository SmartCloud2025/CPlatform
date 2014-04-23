package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.ServiceInfo;

import java.util.List;

import org.apache.catalina.util.ServerInfo;
import org.springframework.stereotype.Repository;

public interface ServiceInfoMapper extends SuperMapper<ServiceInfo> {
    List<ServiceInfo> getAll();

    ServiceInfo getByWebChatId(String webChatId);
}