package cn.tisson.platform.protocol.resp;

import cn.tisson.platform.protocol.bean.Article;
import cn.tisson.util.MessageUtil;

import java.util.List;

/**
 * 回复图文消息
 */
public class NewsRespMsg extends BaseRespMsg {

    public static final String _ArticleCount = "ArticleCount";
    public static final String _Articles = "Article";


    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public NewsRespMsg() {
        super();
        super.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
