package cn.tisson.dbmgr.mapper;


import cn.tisson.dbmgr.model.Text;

import java.util.List;

public interface TextMapper extends SuperMapper<Text> {
    List<Text> getAll();
}