package cn.sitedev.core.properties;

/**
 * @description: 图片码属性类
 * @author: QChen
 * @create: 2018/4/23 0023
 **/
public class ImageCodeProperties {
    /**
     * 宽度
     */
    private int width = 67;
    /**
     * 高度
     */
    private int height = 23;
    /**
     * 长度
     */
    private int length = 4;
    /**
     * 有效期(单位:s)
     */
    private int expireIn = 60;
    /**
     * 需要图片验证码校验的url(如果有多个,用逗号分隔)
     */
    private String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
