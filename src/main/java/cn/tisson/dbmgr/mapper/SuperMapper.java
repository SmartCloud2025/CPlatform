package cn.tisson.dbmgr.mapper;

/**
 * Author Jasic
 * Date 14-1-20.
 */
public interface SuperMapper<E> {

    int deleteByPrimaryKey(Integer pid);

    int insert(E record);

    int insertSelective(E record);

    E selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);
}
