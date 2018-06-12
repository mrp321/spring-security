package cn.sitedev.core.valicode.image;

import cn.sitedev.core.valicode.ValidateCode;

import java.awt.image.BufferedImage;

/**
 * @description: 图片验证码
 * @author: QChen
 * @create: 2018/4/18 0018
 **/
public class ImageCode extends ValidateCode {
    /**
     * 图片
     */
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
