package cn.tisson.dbmgr.mapper;

import cn.tisson.dbmgr.model.ServiceInfo;

import java.util.List;

import org.springframework.stereotype.Repository;

public interface ServiceInfoMapper extends SuperMapper<ServiceInfo> {
    List<ServiceInfo> getAll();
}