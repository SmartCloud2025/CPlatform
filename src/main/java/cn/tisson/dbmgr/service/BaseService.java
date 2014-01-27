package cn.tisson.dbmgr.service;


import cn.tisson.dbmgr.mapper.SuperMapper;

/**
 * Author Jasic
 * Date 14-1-20.
 */
public abstract class BaseService<E> {

    public abstract SuperMapper<E> getMapper();

    public int deleteByPrimaryKey(Integer pid) {
        return getMapper().deleteByPrimaryKey(pid);
    }

    public int insert(E record) {
        return getMapper().insert(record);
    }

    public int insertSelective(E record) {
        return getMapper().insertSelective(record);
    }

    public E selectByPrimaryKey(Integer pid) {
        return getMapper().selectByPrimaryKey(pid);
    }

    public int updateByPrimaryKeySelective(E record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(E record) {
        return getMapper().updateByPrimaryKey(record);
    }
}
