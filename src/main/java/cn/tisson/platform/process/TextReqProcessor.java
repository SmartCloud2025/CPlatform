package cn.tisson.platform.process;

import cn.tisson.platform.protocol.req.TextReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.platform.protocol.resp.TextRespMsg;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jasic
 * Date: 13-12-28
 * 文本消息的处理器
 */
public class TextReqProcessor extends AProcessor<TextReqMsg> {
    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final List<Object> EXCLUDE_DUPLICATE_LIST = new ArrayList<Object>();

    @Override
    protected List<Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_LIST;
    }

    @Override
    protected BaseRespMsg doProcess(TextReqMsg msg) {
        TextRespMsg respMsg = new TextRespMsg();
        respMsg.setToUserName(msg.getFromUserName());
        respMsg.setFromUserName(msg.getToUserName());
        String respText = null;
        try {
            respText = new String("这是回复文件请求".getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        respText = msg.getContent();

        respMsg.setContent(respText);
        respMsg.setCreateTime(System.currentTimeMillis() / 1000);

        return respMsg;
    }
}
