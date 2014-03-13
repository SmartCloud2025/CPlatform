package cn.tisson.dbmgr.mapper;


import cn.tisson.dbmgr.model.ServiceConfig;

import java.util.List;

public interface ServiceConfigMapper extends SuperMapper<ServiceConfig> {

    public abstract List<ServiceConfig> getAll();

}