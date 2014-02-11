package sy.test;

import cn.tisson.dbmgr.service.ActiveArticleService;
import cn.tisson.framework.config.ConfigHandler;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Author Jasic
 * Date 14-1-17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppContextConfig.class)
public class TestMybatis {


    ApplicationContext context;

    @Before
    public void before() {
        ConfigHandler.loadConfigWithoutDB(TestMybatis.class);
        context = new ClassPathXmlApplicationContext("classpath:config/spring*.xml");

    }


    @Test
    public void testNewsMsg() {

        ActiveArticleService service = context.getBean(ActiveArticleService.class);

        List list = service.getListByNewsMsgID(1);
        System.out.println(JSON.toJSON(list));
    }
}
