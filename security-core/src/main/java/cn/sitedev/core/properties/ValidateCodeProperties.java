package cn.sitedev.core.properties;

/**
 * @description: 验证码属性类
 * @author: QChen
 * @create: 2018/4/23 0023
 **/
public class ValidateCodeProperties {
    /**
     * 图片码属性类
     */
    private ImageCodeProperties image = new ImageCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
