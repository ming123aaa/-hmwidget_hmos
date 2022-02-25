package com.ohuang.hmwidget.navigation;


import com.ohuang.hmwidget.ResourceTable;
import ohos.agp.components.*;
import ohos.agp.utils.Color;
import ohos.app.Context;


public class NavigationButton extends ComponentContainer implements ComponentContainer.ArrangeListener {
    private Text text;
    private Image image;
    private Component space;
    private Component spaceTop;
    private Component spaceBottom;



    public NavigationButton(Context context) {
        super(context);
        initView(context);
    }

    public NavigationButton(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initView(context);
    }

    public NavigationButton(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initView(context);
    }

    private void initView(Context context) {
        LayoutScatter.getInstance(context).parse(ResourceTable.Layout_view_navigation_hmwidget, this, true);
        text = (Text) findComponentById(ResourceTable.Id_txt_navigation);
        image = (Image) findComponentById(ResourceTable.Id_img_navigation);
        space = findComponentById(ResourceTable.Id_space_navigation);
        spaceBottom=findComponentById(ResourceTable.Id_space_navigation_bottom);
        spaceTop=findComponentById(ResourceTable.Id_space_navigation_top);
        setArrangeListener(this);
    }

    @Override
    public boolean onArrange(int i, int i1, int i2, int i3) {
        getComponentAt(0).arrange(0, 0,i2, i3);
        return true;
    }



    public void setText(String s) {
        if (s!=null) {
            text.setText(s);
        }
    }

    public void setTextColor(Color c) {
        if (c!=null) {
            text.setTextColor(c);
        }
    }

    public void setTextSize(int size) {
        if (size>-1) {
            text.setTextSize(size);
        }
    }


    public void setImage(int id){
        if (id!=0) {
            image.setPixelMap(id);
        }
    }

    public void setImageSize(int with,int height){
        if (with>=0) {
            image.setWidth(with);
        }
        if (height>=0) {
            image.setHeight(height);
        }
    }

    public void  setSpace(int height){
        if (height>=0) {
            space.setHeight(height);
        }
    }

    public void  setSpaceTop(int height){
        if (height>=0) {
            spaceTop.setHeight(height);
        }
    }


    public void  setSpaceBottom(int height){
        if (height>=0) {
            spaceBottom.setHeight(height);
        }
    }

    public void setImageVisible(int visible){
        image.setVisibility(visible);
    }

    public void setTextVisible(int visible){
        text.setVisibility(visible);
    }
}
