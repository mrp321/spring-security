package cn.sitedev.core.properties;

/**
 * @description: 图片码属性类
 * @author: QChen
 * @create: 2018/4/23 0023
 **/
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 宽度
     */
    private int width = 67;
    /**
     * 高度
     */
    private int height = 23;

    public ImageCodeProperties() {
        // 设置默认长度为4
        this.setLength(4);
    }

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

}
