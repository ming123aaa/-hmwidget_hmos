# HMWidget

依赖：

```

 implementation 'io.gitee.a3077932030:hmwidget:1.0.2'
 
```

### HMWidget 鸿蒙小组件

鸿蒙版Fragment:  Fragment,FragmentAbilitySlice,FragmentFraction

FragmentPagerProvider:用于fragment与pageSlice配合使用

livedata:类似于ActiveData     Livedata LivedataBus 

ListContainer:   ListContainerProvider，ListContainerProvider2

Navigation 底部导航栏组件: NavigationView,NavigationBuilder,NavigationTab



#### 版本:

1.0.1： 添加Fragment,FragmentPagerProvider,livedata,Navigation 等。
1.0.2： 添加FragmentAbility


### 使用



####  Fragment的使用

 创建fragment

```java
//需要继承Fragment
public  class DemoFragment extends Fragment {

   //在这里创建 Component
    @Override
    protected Component onCreateComponent(LayoutScatter scatter, ComponentContainer container) {
        return scatter.parse(ResourceTable.Layout_fragment_img,container,false);
    }
    
    //在这里完成Component初始化操作
     @Override
    protected void initComponent() {

        image = findComponentById(ResourceTable.Id_image);
   
    }
      
}
```

在Abilityslice中添加fragment。

```java
//需要继承FragmentAbilitySlice
public class MainAbilitySlice extends FragmentAbilitySlice {
       private ComponentContainer stackLayout;
       private Fragment thisFragment,demoFg;
       
 @Override
    public void onStart(Intent intent) {
     super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        stackLayout = (ComponentContainer) findComponentById(ResourceTable.Id_stackLayout);
        demoFg=new DemoFragment();
        getFragmentManager().beginTransaction()//获取FragmentManager
            .add(ResourceTable.Id_stackLayout, thisFragment)//通过add(id,fragment)方法添加到ComponentContainer
            .commit();//提交后才生效
       thisFragment=demoFg;
    }
    
    
    //fragment的切换调用以下方法
    public void showFragment(Fragment fg) {
        if (fg != thisFragment) {
            if (fg.isAdd()) {//判断当前fragment是否已经添加  已添加让它显示即可
                getFragmentManager().beginTransaction()
                        .hide(thisFragment) //先隐藏当前显示的fragment
                        .show(fg)//再显示fragment
                        .commit();
            } else {//若没有添加 则需要添加Fragment
                getFragmentManager().beginTransaction()
                        .hide(thisFragment)
                        .add(ResourceTable.Id_stackLayout, fg)
                        .commit();
            }
            thisFragment = fg;
        }
    }
}
```

在Fraction内添加fragment  使用方法和Abilityslice相同

```
public class demoFraction extends FragmentFraction{

}
```

fragment嵌套fragment，需要获取getChildFragmentManager()对fragment的操作。

```
public class ImgFragment extends Fragment {

    @Override
    protected void initComponent() {
        DemoFragment demoFg=new DemoFragment();
         getChildFragmentManager().beginTransaction()//需要获取ChildFragmentManager
            .add(ResourceTable.Id_stackLayout, thisFragment)//通过add(id,fragment)方法添加到ComponentContainer
            .commit();//提交后才生效
    
    }
```

#### Fragment配合PageSlice使用

pageSlice可以配合fragment一起使用。这里使用到FragmentPagerProvider2这个类,需要实现getFragment()和getCount()方法,分别返回当前索引的fragment和fragment的总个数。

```java
    private PageSlider pageSlider;
    private List<Fragment> fragmentList;
    private FragmentPagerProvider2 pageSliderProvider;

@Override
    protected void initComponent() {
        pageSlider = findComponentById(ResourceTable.Id_page_slider);
        tabList = findComponentById(ResourceTable.Id_tab_list);
        addFragment();//向fragmentList添加fragment
//创建FragmentPagerProvider2  需要传入fragmentManager
        pageSliderProvider = new FragmentPagerProvider2(getChildFragmentManager()) {
            @Override
            public Fragment getFragment(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        pageSlider.setProvider(pageSliderProvider); //添加Provider

        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int i, float v, int i1) {

            }

            @Override
            public void onPageSlideStateChanged(int i) {

            }

            @Override
            public void onPageChosen(int i) {
                
                //以下方法用于回收无用的fragment,以及对fragment的可见性分发
                 //所以在onPageChosen必须加上以下方法
                //selectAndRemove(当前选中界面的索引, pageSlider界面缓存大小默认为1)
                 
                pageSliderProvider.selectAndRemove(i, pageSlider.getCachedPagesLimit()); 
                
            }
        });
        /*
        pageSlider.setCurrentPage(i); //调用setCurrentPage(i)方法会跳转
        pageSliderProvider.selectAndRemove(i,pageSlider.getCachedPagesLimit()); //所以同样要调用这个方法
        */
    }
```

#### NavigationView使用

NavigationView是底部导航栏组件,使用方式如下:

```java
navigationButton = (NavigationView) findComponentById(ResourceTable.Id_navigationView);


NavigationBuilder navigationBuilder = navigationButton.getNavigationBuilder().
        setTextColor(new Color(Color.rgb(0, 0, 0))) //设置字体颜色
        .setSelectTextColor(new Color(Color.rgb(255, 0, 0))) //设置选中字体颜色
        .setDefaultDrawableId(ResourceTable.Media_icon) //设置图标
        .setSelectDrawableId(ResourceTable.Media_icon_hj) //设置选中状态的图标
        .setSpaceTop(10) //顶部的空间
        .setSpaceBottom(10) //距离底部的空间
        .setSpace(5);//图标和文字的空间
NavigationTab navigationTab = navigationBuilder.setText("pageSlice").build();//设置文字 并构建tab
NavigationTab navigationTab1 = navigationBuilder.setText("list").build();//设置文字 并构建tab

List<NavigationTab> navigationTabs = new ArrayList<>();
navigationTabs.add(navigationTab);
navigationTabs.add(navigationTab1);
navigationButton.setTabList(navigationTabs);//设置tab后显示 
navigationButton.setSelect(0);//设置选中的按钮 索引

navigationButton.setOnSelectListener(new NavigationView.OnSelectListener() {
            //按钮选中时会调用改方法     return true 可拦截选中事件
            @Override
            public boolean onSelect(NavigationView navigationView, int index) {
           
                return false;

            }
        });
```

#### livedata使用

```java
   LiveData<String> data=new LiveData<>();
   
   data.addObserver(new Observer<String>() {//添加观察者 调用setData()会调用onchange
        @Override
        public void onchange(String s) {
            
        }
    });
    
     //传入ILifecycle可自动检测生命周期 在界面可见时才会调用onchange方法
    data.addObserver(this,new Observer<String>() {
   
        @Override
        public void onchange(String s) {
            
        }
    });
    
    data.setData();//发送数据
    data.postData();//非主线程发送数据
    
    data.addObserverForSticky();//添加带粘性事件的观察者
    
    //在使用完毕后需要移除Observer 避免内存泄露
    //若添加Observer时传入ILifecycle则会根据生命周期自动的移除
    data.removeObserver();
    data.removeAllObserver();
    
```

