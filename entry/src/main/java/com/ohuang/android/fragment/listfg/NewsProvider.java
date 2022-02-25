package com.ohuang.android.fragment.listfg;

import com.bumptech.glide.Glide;
import com.ohuang.android.ResourceTable;
import com.ohuang.android.bean.WYNewsBean;
import com.ohuang.hmwidget.listContainer.ListContainerProvider;
import com.ohuang.hmwidget.listContainer.ListContainerProvider2;

import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;

public class NewsProvider extends ListContainerProvider2 {
    private List<WYNewsBean.ResultBean> list;
    private Context context;

    public NewsProvider(List<WYNewsBean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ListContainerProvider.ViewHolder onCreateViewHolder(ComponentContainer parent, int viewType, int position) {
        return new NewsViewHolder(LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ListContainerProvider.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).text.setText(list.get(position).getTitle());
            ((NewsViewHolder) holder).text_time.setText(list.get(position).getPasstime());
            Glide.with(context).load(list.get(position).getImage()).into(((NewsViewHolder) holder).image);

        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class NewsViewHolder extends ListContainerProvider.ViewHolder {
        private Text text;
        private Image image;
        private Text text_time;

        public NewsViewHolder(Component component) {
            super(component);
            text = (Text) component.findComponentById(ResourceTable.Id_tv_tile);
            text_time = (Text) component.findComponentById(ResourceTable.Id_tv_time);
            image = (Image) component.findComponentById(ResourceTable.Id_image);
        }
    }
}
