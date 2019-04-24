package com.zhiyicx.thinksnsplus.modules.personal_center.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.em.manager.util.TSEMConstants;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.Letter;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.CircleDetailActivity;
import com.zhiyicx.thinksnsplus.modules.circle.pre.PreCircleActivity;
import com.zhiyicx.thinksnsplus.modules.information.infodetails.InfoDetailsActivity;
import com.zhiyicx.thinksnsplus.modules.q_a.detail.answer.AnswerDetailsActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.concurrent.TimeUnit;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;

/**
 * @author LiuChao
 * @describe
 * @date 2017/3/10
 * @contact email:450127106@qq.com
 */

public class PersonalCenterDynamicListForWardInfo extends PersonalCenterDynamicListBaseItem {
    public PersonalCenterDynamicListForWardInfo(Context context) {
        super(context);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_personal_center_dynamic_list_forward_info;
    }

    @Override
    public boolean isForViewType(DynamicDetailBeanV2 item, int position) {
        Letter letter = item.getMLetter();
        return item.getFeed_mark() != null &&
                item.getFeed_from() != DEFAULT_ADVERT_FROM_TAG &&
                (item.getImages() == null || item.getImages().isEmpty()) &&
                item.getVideo() == null &&
                letter != null &&
                TSEMConstants.BUNDLE_CHAT_MESSAGE_FORWARD_TYPE_INFO.equals(letter.getType());
    }

    @Override
    public void convert(ViewHolder holder, final DynamicDetailBeanV2 dynamicBean, DynamicDetailBeanV2 lastT, int position, int itemCounts) {
        super.convert(holder, dynamicBean, lastT, position,itemCounts);

        holder.setText(R.id.tv_forward_name, dynamicBean.getMLetter().getName());
        holder.setText(R.id.tv_forward_content, dynamicBean.getMLetter().getContent());
        ImageView imageView = holder.getImageViwe(R.id.iv_forward_image);
        String path = dynamicBean.getMLetter().getImage();
        imageView.setVisibility(TextUtils.isEmpty(path) ? View.GONE : View.VISIBLE);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.shape_default_image)
                .error(R.drawable.shape_default_image)
                .into(imageView);

        RxView.clicks(holder.getView(R.id.tv_forward_container))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> InfoDetailsActivity.startInfoDetailsActivity(mContext,
                        Long.parseLong(dynamicBean.getMLetter().getId())));
        RxView.clicks(holder.getView(R.id.tv_forward_name))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> InfoDetailsActivity.startInfoDetailsActivity(mContext,
                        Long.parseLong(dynamicBean.getMLetter().getId())));
        RxView.clicks(holder.getView(R.id.tv_forward_content))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> InfoDetailsActivity.startInfoDetailsActivity(mContext,
                        Long.parseLong(dynamicBean.getMLetter().getId())));
    }
}
