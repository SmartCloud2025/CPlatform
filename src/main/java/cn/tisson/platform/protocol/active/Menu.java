package cn.tisson.platform.protocol.active;

import java.util.List;

/**
 * Author Jasic
 * Date 14-1-24.
 */
public class Menu {

    private String type;
    private String name;

    private String key;
    private String url;


    List<SubMenu> sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SubMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<SubMenu> sub_button) {
        this.sub_button = sub_button;
    }
}
