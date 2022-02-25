package com.ohuang.android.fragment;

import com.bumptech.glide.Glide;
import com.ohuang.android.ResourceTable;
import com.ohuang.android.base.BaseFragment;
import ohos.agp.components.Image;

public class ImgFragment extends BaseFragment {
    private boolean lastVisible=false;
    private boolean isLoad=false;

    @Override
    public int getXmlId() {
        return ResourceTable.Layout_fragment_img;
    }

    private Image image;
    private String url="https://himg.bdimg.com/sys/" +
            "portrait/item/" +
            "public.1.284f0d29." +
            "hC_mmPJzGF2CvFYeVQfGng.jpg";

    @Override
    protected void initComponent() {

        image = findComponentById(ResourceTable.Id_image);
        Glide.with(getContext())
                .load(url)
                .into(image);

    }


}
