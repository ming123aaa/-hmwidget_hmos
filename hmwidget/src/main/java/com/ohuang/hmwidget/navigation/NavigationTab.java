package com.ohuang.hmwidget.navigation;


import ohos.agp.components.Component;
import ohos.agp.utils.Color;
import ohos.app.Context;

public class NavigationTab {
   private Color textColor;
   private Color selectTextColor;
   private Object data;
   private int imgVisible= Component.VISIBLE;

   private int textVisible= Component.VISIBLE;
   private int defaultDrawableId;
   private int selectDrawableId;

   private int space=0;
   private  int spaceTop=0;
   private int spaceBottom=0;

   private String text;
   private int textSize=-1;

   private int imgw=100,imgh=100;




   public  NavigationTab(NavigationBuilder navigationBuilder){
      textColor=navigationBuilder.textColor;
      selectTextColor=navigationBuilder.selectTextColor;
      imgVisible=navigationBuilder.ImgVisible;
      textVisible=navigationBuilder.TextVisible;
      defaultDrawableId=navigationBuilder.defaultDrawableId;
      selectDrawableId=navigationBuilder.selectDrawableId;
      space=navigationBuilder.space;
      spaceTop= navigationBuilder.spaceTop;
      spaceBottom= navigationBuilder.spaceBottom;
      data=navigationBuilder.data;
      imgh=navigationBuilder.imgh;
      imgw=navigationBuilder.imgw;
      text=navigationBuilder.text;

   }


   public Color getTextColor() {
      return textColor;
   }

   public Color getSelectTextColor() {
      return selectTextColor;
   }

   public int getImgVisible() {
      return imgVisible;
   }

   public int getTextVisible() {
      return textVisible;
   }

   public int getDefaultDrawableId() {
      return defaultDrawableId;
   }

   public int getSelectDrawableId() {
      return selectDrawableId;
   }

   public int getSpace() {
      return space;
   }

   public Object getData() {
      return data;
   }

   public String getText() {
      return text;
   }

   public int getTextSize() {
      return textSize;
   }

   public int getImgw() {
      return imgw;
   }

   public int getImgh() {
      return imgh;
   }

   public int getSpaceTop() {
      return spaceTop;
   }

   public int getSpaceBottom() {
      return spaceBottom;
   }

   public NavigationButton create(Context context){
      NavigationButton navigationButton=new NavigationButton(context);
      navigationButton.setSpace(space);
      navigationButton.setSpaceTop(spaceTop);
      navigationButton.setSpaceBottom(spaceBottom);

      navigationButton.setImage(defaultDrawableId);
      navigationButton.setImageSize(imgw,imgh);
      navigationButton.setText(text);
      navigationButton.setTextSize(textSize);
      navigationButton.setTextColor(textColor);

      navigationButton.setImageVisible(imgVisible);
      navigationButton.setTextVisible(textVisible);

      return navigationButton;
   }
}
