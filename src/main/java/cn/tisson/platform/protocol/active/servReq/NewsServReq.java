package cn.tisson.platform.protocol.active.servReq;


import cn.tisson.platform.protocol.bean.Article;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Jasic
 * Date 14-2-8.
 */
public class NewsServReq extends BaseServReq {
    public static final String MSGTYPE = "news";

    private News news;

    public NewsServReq(String touser) {
        super(touser, MSGTYPE);
        news = new News();
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public static class News {
        private List<Article> articles;

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }
    }

    public static void main(String[] args) {
        NewsServReq newsMsg = new NewsServReq("Jasic");
        List<Article> articles = new ArrayList<Article>();
        Article article1 = new Article();
        article1.setTitle("Happy Day");
        article1.setPicUrl("PIC_URL");
        article1.setDescription("Is Really A Happy Day");
        article1.setUrl("URL");
        articles.add(article1);

        Article article2 = new Article();
        article2.setTitle("Happy Day");
        article2.setPicUrl("PIC_URL");
        article2.setDescription("Is Really A Happy Day");
        article2.setUrl("URL");
        articles.add(article2);

        newsMsg.getNews().setArticles(articles);

        System.out.println(JSON.toJSONString(newsMsg));
    }
}
