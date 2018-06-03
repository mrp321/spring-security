package cn.sitedev.core.support;

/**
 * /**
 *
 * @description: 返回信息封装
 * @author: QChen
 * @create: 2018/4/16 0016
 **/
public class SimpleResponse {

    private String content;

    public SimpleResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
