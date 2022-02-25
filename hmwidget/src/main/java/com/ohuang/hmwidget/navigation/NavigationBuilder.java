package com.ohuang.hmwidget.navigation;


import ohos.agp.components.Component;
import ohos.agp.utils.Color;

public class NavigationBuilder {
    Color textColor;
    Color selectTextColor;
    Object data;

    int ImgVisible= Component.VISIBLE;
    int TextVisible= Component.VISIBLE;
    int defaultDrawableId;
    int selectDrawableId;

    int space=0;

    int spaceTop=0;
    int spaceBottom=0;


    int imgw=100,imgh=100;

    String text;
    int textSize=-1;

    /**
     * 设置显示的图片大小
     * @param with
     * @param height
     * @return
     */
    public NavigationBuilder setImageSize(int with,int height){
        if (with>=0){
            imgw=with;
        }
        if (height>=0){
            imgh=height;
        }
        return this;
    }


    /**
     * 按钮文字颜色
     * @param textColor
     * @return
     */
    public NavigationBuilder setTextColor(Color textColor) {
        this.textColor = textColor;
        return this;
    }

    /**
     * 按钮选中时的文字颜色
     * @param selectTextColor
     * @return
     */
    public NavigationBuilder setSelectTextColor(Color selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }


    /**
     * true为隐藏图片
     * @param b
     * @return
     */
    public NavigationBuilder NoImage(boolean b) {
        if (b){
            ImgVisible = Component.INVISIBLE;
        }else {
            ImgVisible = Component.VISIBLE;
        }

        return this;
    }

    /**
     * true为隐藏文字
     * @param b
     * @return
     */
    public NavigationBuilder NoText(boolean b) {
        if (b){
            TextVisible = Component.INVISIBLE;
        }else {
            TextVisible = Component.VISIBLE;
        }
        return this;
    }

    /**
     * 未选中时的图片
     * @param defaultDrawable 默认图片的资源Id
     * @return
     */
    public NavigationBuilder setDefaultDrawableId(int defaultDrawable) {
        this.defaultDrawableId = defaultDrawable;
        return this;
    }

    /**
     * 选中时的图片
     * @param selectDrawable
     * @return
     */
    public NavigationBuilder setSelectDrawableId(int selectDrawable) {
        this.selectDrawableId = selectDrawable;
        return this;
    }

    /**
     * 文字和图片之间的空间大小
     * @param space
     * @return
     */
    public NavigationBuilder setSpace(int space) {
        this.space = space;
        return this;
    }

    /**
     * 顶部空间
     * @param space
     * @return
     */
    public NavigationBuilder setSpaceTop(int space) {
        this.spaceTop = space;
        return this;
    }


    /**
     * 底部空间
     * @param space
     * @return
     */
    public NavigationBuilder setSpaceBottom(int space) {
        this.spaceBottom = space;
        return this;
    }


    /**
     * 设置文字
     * @param text
     * @return
     */
    public NavigationBuilder setText(String text) {
        this.text=text;
        return this;
    }

    /**
     * 文字大小
     * @param textSize
     * @return
     */
    public NavigationBuilder setTextSize(int textSize) {
        this.textSize=textSize;
        return this;
    }

    /**
     * 添加数据
     * @param data
     */
    public NavigationBuilder setData(Object data) {
        this.data = data;
        return this;
    }

    public NavigationTab build(){
        return new NavigationTab(this);
    }
}
