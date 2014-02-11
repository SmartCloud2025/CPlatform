package cn.tisson.dbmgr.service;

import cn.tisson.dbmgr.mapper.SuperMapper;
import cn.tisson.dbmgr.mapper.TextMapper;
import cn.tisson.dbmgr.model.Text;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Jasic
 * Date 14-1-26.
 */
@Service
public class TextService extends BaseService<Text> {

    @Resource
    private TextMapper mapper;

    public List<Text> getAll() {
        return mapper.getAll();
    }


    @Override
    public SuperMapper<Text> getMapper() {

        return mapper;
    }
}
