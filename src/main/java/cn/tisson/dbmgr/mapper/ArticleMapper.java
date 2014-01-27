package cn.tisson.dbmgr.mapper;


import cn.tisson.dbmgr.model.Article;
import org.springframework.stereotype.Repository;

public interface ArticleMapper extends SuperMapper<Article> {
    int deleteByPrimaryKey(Integer pid);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
}