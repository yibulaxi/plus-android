package com.zhiyicx.thinksnsplus.modules.personal_center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.TouristConfig;
import com.zhiyicx.baseproject.impl.photoselector.DaggerPhotoSelectorImplComponent;
import com.zhiyicx.baseproject.impl.photoselector.ImageBean;
import com.zhiyicx.baseproject.impl.photoselector.PhotoSelectorImpl;
import com.zhiyicx.baseproject.impl.photoselector.PhotoSeletorImplModule;
import com.zhiyicx.baseproject.impl.photoselector.Toll;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.share.Share;
import com.zhiyicx.baseproject.widget.InputPasswordView;
import com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow;
import com.zhiyicx.baseproject.widget.popwindow.PayPopWindow;
import com.zhiyicx.common.BuildConfig;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.TextViewUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.utils.UIUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.common.utils.recycleviewdecoration.LinearDecoration;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.fordownload.TSListFragmentForDownload;
import com.zhiyicx.thinksnsplus.config.UserPermissions;
import com.zhiyicx.thinksnsplus.data.beans.AnimationRectBean;
import com.zhiyicx.thinksnsplus.data.beans.AuthBean;
import com.zhiyicx.thinksnsplus.data.beans.Avatar;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.beans.report.ReportResourceBean;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseDynamicRepository;
import com.zhiyicx.thinksnsplus.i.OnUserInfoClickListener;
import com.zhiyicx.thinksnsplus.modules.aaaat.AtUserActivity;
import com.zhiyicx.thinksnsplus.modules.aaaat.AtUserListFragment;
import com.zhiyicx.thinksnsplus.modules.chat.ChatActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem;
import com.zhiyicx.thinksnsplus.modules.gallery.GalleryActivity;
import com.zhiyicx.thinksnsplus.modules.password.findpassword.FindPasswordActivity;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardAnswer;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardCircle;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardInfo;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardMediaFeed;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardPost;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardQuestion;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForWardWordFeed;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListForZeroImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForEightImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForFiveImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForFourImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForNineImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForOneImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForSevenImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForShortVideo;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForSixImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForThreeImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterDynamicListItemForTwoImage;
import com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterHeaderViewItem;
import com.zhiyicx.thinksnsplus.modules.report.ReportActivity;
import com.zhiyicx.thinksnsplus.modules.report.ReportType;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView;
import com.zhiyicx.thinksnsplus.modules.wallet.reward.RewardFragment;
import com.zhiyicx.thinksnsplus.modules.wallet.reward.RewardType;
import com.zhiyicx.thinksnsplus.modules.wallet.sticktop.StickTopFragment;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;
import com.zhiyicx.thinksnsplus.widget.DynamicEmptyItem;
import com.zhiyicx.thinksnsplus.widget.comment.DynamicListCommentView;
import com.zhiyicx.thinksnsplus.widget.comment.DynamicNoPullRecycleView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zycx.shortvideo.view.AutoPlayScrollListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerManager;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow.POPUPWINDOW_ALPHA;
import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_DETAIL_DATA;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_DETAIL_DATA_POSITION;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_VIDEO_STATE;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.LOOK_COMMENT_MORE;
import static com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment.ITEM_SPACING;
import static com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterHeaderViewItem.STATUS_RGB;
import static com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterHeaderViewItem.TOOLBAR_BLACK_ICON;
import static com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterHeaderViewItem.TOOLBAR_DIVIDER_RGB;
import static com.zhiyicx.thinksnsplus.modules.personal_center.adapter.PersonalCenterHeaderViewItem.TOOLBAR_WHITE_ICON;

/**
 * @author LiuChao
 * @describe 用户个人中心页面
 * @date 2017/3/7
 * @contact email:450127106@qq.com
 */

public class PersonalCenterFragment extends TSListFragmentForDownload<PersonalCenterContract.Presenter, DynamicDetailBeanV2> implements
        PersonalCenterContract.View,
        DynamicListBaseItem.OnReSendClickListener, DynamicNoPullRecycleView.OnCommentStateClickListener<DynamicCommentBean>,
        DynamicListCommentView.OnCommentClickListener, DynamicListBaseItem.OnMenuItemClickLisitener,
        DynamicListBaseItem.OnImageClickListener, OnUserInfoClickListener, DynamicListCommentView.OnMoreCommentClickListener,
        MultiItemTypeAdapter.OnItemClickListener,
        PhotoSelectorImpl.IPhotoBackListener, TextViewUtils.OnSpanTextClickListener, ZhiyiVideoView.ShareInterface {

    public static final String PERSONAL_CENTER_DATA = "personal_center_data";
    public static final String VIDEO_FROME = "personal";

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.rl_toolbar_container)
    RelativeLayout mRlToolbarContainer;
    @BindView(R.id.tv_follow)
    TextView mTvFollow;
    @BindView(R.id.ll_follow_container)
    LinearLayout mLlFollowContainer;
    @BindView(R.id.ll_reward_container)
    LinearLayout mLLRewardContainer;
    @BindView(R.id.view_line_reward)
    View mLineReward;
    @BindView(R.id.ll_chat_container)
    LinearLayout mLlChatContainer;
    @BindView(R.id.ll_bottom_container)
    LinearLayout mLlBottomContainer;
    @BindView(R.id.ll_toolbar_container_parent)
    LinearLayout mLlToolbarContainerParent;
    @BindView(R.id.iv_refresh)
    ImageView mIvRefresh;

    private PersonalCenterHeaderViewItem mPersonalCenterHeaderViewItem;
    // 上一个页面传过来的用户信息
    private UserInfoBean mUserInfoBean;
    private PhotoSelectorImpl mPhotoSelector;
    private ActionPopupWindow mDeletCommentPopWindow;
    private ActionPopupWindow mDeletDynamicPopWindow;
    private ActionPopupWindow mReSendCommentPopWindow;
    private ActionPopupWindow mReSendDynamicPopWindow;
    private ActionPopupWindow mTopBarMorePopWindow;
    private PayPopWindow mPayImagePopWindow;
    private int mCurrentPostion;// 当前评论的动态位置
    private long mReplyToUserId;// 被评论者的 id
    private BaseDynamicRepository.MyDynamicTypeEnum mDynamicType = BaseDynamicRepository.MyDynamicTypeEnum.ALL; //type = users 时可选，null-全部
    private LinearDecoration mLinearDecoration;
    private Subscription showComment;
    // paid-付费动态 pinned - 置顶动态

    /**
     * 跳转到当前的个人中心页面
     */
    public static void startToPersonalCenter(Context context, UserInfoBean userInfoBean) {
        if (userInfoBean == null || context == null) {
            return;
        }
        if (userInfoBean.getHas_deleted() || !TextUtils.isEmpty(userInfoBean.getDeleted_at())) {
            try {
                if (context instanceof TSActivity) {
                    if ((((TSActivity) context).getContanierFragment() instanceof TSFragment)) {
                        ((TSFragment) ((TSActivity) context).getContanierFragment()).showSnackWarningMessage(context.getString(R.string
                                .user_had_deleted));
                    }
                } else {
                    ToastUtils.showToast(context, R.string.user_had_deleted);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(context, R.string.user_had_deleted);
            }

            return;
        }
// 新需求，小助手也进入他自己的主页
//        String tsHelperUrl = checkHelperUrl(context, userInfoBean.getUser_id());
//        if (AppApplication.getMyUserIdWithdefault() != userInfoBean.getUser_id() && !TextUtils.isEmpty(tsHelperUrl)) {
//            CustomWEBActivity.startToWEBActivity(context, tsHelperUrl);
//        } else {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PersonalCenterFragment.PERSONAL_CENTER_DATA, userInfoBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
//        }

    }

    public static PersonalCenterFragment initFragment(Bundle bundle) {
        PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
        personalCenterFragment.setArguments(bundle);
        return personalCenterFragment;
    }

    @Override
    protected boolean setUseInputCommentView() {
        return true;
    }

    @Override
    protected boolean setUseShadowView() {
        return true;
    }

    @Override
    protected int getstatusbarAndToolbarHeight() {
        if (setUseSatusbar()) {
            return 0;
        }
        return super.getstatusbarAndToolbarHeight();
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        mLinearDecoration = new LinearDecoration(0, ConvertUtils.dp2px(getContext(), getItemDecorationSpacing()), 0, 0);
        return mLinearDecoration;
    }

    @Override
    protected float getItemDecorationSpacing() {
        return ITEM_SPACING;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected boolean usePermisson() {
        return true;
    }

    @Override
    protected boolean needCenterLoadingDialog() {
        return true;
    }

    @Override
    protected View getRightViewOfMusicWindow() {
        return mIvMore;
    }

    @Override
    protected boolean isRefreshEnable() {
        return false;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseCenterLoading() {
        return true;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        setLoadViewHolderImag(R.mipmap.img_default_nobody);
        // 初始化图片选择器
        mPhotoSelector = DaggerPhotoSelectorImplComponent
                .builder()
                .photoSeletorImplModule(new PhotoSeletorImplModule(this, this, PhotoSelectorImpl
                        .SHAPE_RCTANGLE))
                .build().photoSelectorImpl();
        initToolBar();
        View mFooterView = new View(getContext());
        mFooterView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        mHeaderAndFooterWrapper.addFootView(mFooterView);
        mPersonalCenterHeaderViewItem = new PersonalCenterHeaderViewItem(getActivity(), this, mPhotoSelector, mRvList, mHeaderAndFooterWrapper,
                mLlToolbarContainerParent);
        mPersonalCenterHeaderViewItem.initHeaderView(false);
        mPersonalCenterHeaderViewItem.setViewColorWithAlpha(mLlToolbarContainerParent, STATUS_RGB, 255);
        mPersonalCenterHeaderViewItem.setViewColorWithAlpha(mLlToolbarContainerParent.findViewById(R.id.v_horizontal_line), TOOLBAR_DIVIDER_RGB, 255);
        mPersonalCenterHeaderViewItem.setToolbarIconColor(Color.argb(255, TOOLBAR_BLACK_ICON[0],
                TOOLBAR_BLACK_ICON[1], TOOLBAR_BLACK_ICON[2]));
//        setOverScroll(false, null);
        mLinearDecoration.setHeaderCount(mHeaderAndFooterWrapper.getHeadersCount());
        mLinearDecoration.setFooterCount(mHeaderAndFooterWrapper.getFootersCount());
        mSystemConfigBean = mPresenter.getSystemConfigBean();
        try {
            if (!mSystemConfigBean.getSite().getReward().hasStatus()) {
                mLineReward.setVisibility(View.GONE);
                mLLRewardContainer.setVisibility(View.GONE);
            } else {
                mLineReward.setVisibility(View.VISIBLE);
                mLLRewardContainer.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException e) {
            mLineReward.setVisibility(View.GONE);
            mLLRewardContainer.setVisibility(View.GONE);
        }

    }

    private void initListener() {
        // 添加打赏点击事件
        RxView.clicks(mLLRewardContainer)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> RewardFragment.startRewardActivity(getContext(), RewardType.USER, mUserInfoBean.getUser_id()));

        // 添加关注点击事件
        RxView.clicks(mLlFollowContainer)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    // 表示第一次进入界面加载正确的关注状态，后续才能进行关注操作
                    mLlFollowContainer.setEnabled(false);
                    mPresenter.handleFollow(mUserInfoBean);
                });

        // 添加聊天点击事件
        RxView.clicks(mLlChatContainer)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> ChatActivity.startChatActivity(mActivity, String.valueOf(mUserInfoBean.getUser_id()),
                        EaseConstant.CHATTYPE_SINGLE));


        // 自动播放 - 滑出屏幕暂停也在这里面
        mRvList.addOnScrollListener(new AutoPlayScrollListener() {
            @Override
            public int getPlayerViewId() {
                return R.id.videoplayer;
            }

            @Override
            public boolean canAutoPlay() {
                // NetUtils.isWifiConnected(getContext().getApplicationContext())
                // 暂时关闭滑动自动播放
                return false;
            }
        });
    }

    @Override
    protected boolean setUseInputPsdView() {
        return true;
    }

    @Override
    public void onSureClick(View v, String text, InputPasswordView.PayNote payNote) {
        mPresenter.payNote(payNote.dynamicPosition, payNote.imagePosition, payNote.note, payNote.isImage, payNote.psd);
    }

    @Override
    public void onForgetPsdClick() {
        showInputPsdView(false);
        startActivity(new Intent(getActivity(), FindPasswordActivity.class));
    }

    @Override
    public void onCancle() {
        dismissSnackBar();
        mPresenter.canclePay();
        showInputPsdView(false);
    }

    @Override
    protected void requestNetData(Long maxId, boolean isLoadMore) {
        if (mUserInfoBean.getUser_id() == null) {
            requestData(mUserInfoBean.getUser_id() == null ? mUserInfoBean.getName() : mUserInfoBean.getUser_id());
            return;
        }
        mPresenter.requestNetData(maxId, isLoadMore, mUserInfoBean.getUser_id());
    }

    @Override
    protected void requestCacheData(Long maxId, boolean isLoadMore) {
        mPresenter.requestCacheData(maxId, isLoadMore, 0);
    }

    @Override
    public void onUserInfoClick(UserInfoBean userInfoBean) {
        // 如果当前页面的主页已经是当前这个人了，不就用跳转了
        if (userInfoBean == null || mUserInfoBean == null) {
            return;
        }
        if ((userInfoBean.getUser_id() == null && TextUtils.isEmpty(userInfoBean.getName()))) {
            return;
        }

        if (!TextUtils.isEmpty(userInfoBean.getName()) && !userInfoBean.getName().equals(mUserInfoBean.getName())) {
            PersonalCenterFragment.startToPersonalCenter(getContext(), userInfoBean);
            return;
        }

        if (userInfoBean.getUser_id() != null && userInfoBean.getUser_id().intValue() != mUserInfoBean.getUser_id().intValue()) {
            PersonalCenterFragment.startToPersonalCenter(getContext(), userInfoBean);
        }
    }

    @Override
    protected void setLoadingViewHolderClick() {
        super.setLoadingViewHolderClick();
        requestData(mUserInfoBean.getUser_id() == null ? mUserInfoBean.getName() : mUserInfoBean.getUser_id());
    }

    @Override
    protected MultiItemTypeAdapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter(getContext(), mListDatas);
        // 按照添加顺序，先判断成功后，后面的item就不会继续判断了，类似if else
        setAdapter(adapter, new PersonalCenterDynamicListForZeroImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForOneImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForTwoImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForThreeImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForFourImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForFiveImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForSixImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForSevenImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForEightImage(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListItemForNineImage(getContext()));

        setAdapter(adapter, new PersonalCenterDynamicListForWardAnswer(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListForWardCircle(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListForWardInfo(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListForWardMediaFeed(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListForWardPost(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListForWardQuestion(getContext()));
        setAdapter(adapter, new PersonalCenterDynamicListForWardWordFeed(getContext()));

        setAdapter(adapter, new PersonalCenterDynamicListItemForShortVideo(getContext(), this) {
            @Override
            protected String videoFrom() {
                return VIDEO_FROME;
            }
        });
        DynamicEmptyItem emptyItem = new DynamicEmptyItem();
        adapter.addItemViewDelegate(emptyItem);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected void initData() {
        mUserInfoBean = getArguments().getParcelable(PERSONAL_CENTER_DATA);
        if (mUserInfoBean != null) {
            requestData(mUserInfoBean.getUser_id() == null ? mUserInfoBean.getName() : mUserInfoBean.getUser_id());
        }
        super.initData();
    }

    /**
     * 获取服务器数据
     */
    private void requestData(Object userId) {
        // 获取个人主页用户信息，显示在headerView中
        mPresenter.setCurrentUserInfo(userId);
    }

    @Override
    public void onImageClick(ViewHolder holder, DynamicDetailBeanV2 dynamicBean, int position) {
        int dynamicPosition = holder.getAdapterPosition() - mHeaderAndFooterWrapper.getHeadersCount();
        if (!TouristConfig.DYNAMIC_BIG_PHOTO_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }

        DynamicDetailBeanV2.ImagesBean img = dynamicBean.getImages().get(position);
        Boolean canLook = !(img.isPaid() != null && !img.isPaid() && img.getType().equals(Toll.LOOK_TOLL_TYPE));
        if (!canLook) {
            initImageCenterPopWindow(dynamicPosition, position, dynamicBean.getImages().get(position).getAmount(),
                    dynamicBean.getImages().get(position).getPaid_node(), R.string.buy_pay_desc, true);
            return;
        }

        List<DynamicDetailBeanV2.ImagesBean> tasks = dynamicBean.getImages();
        List<ImageBean> imageBeanList = new ArrayList<>();
        ArrayList<AnimationRectBean> animationRectBeanArrayList
                = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (i >= 9) {
                continue;
            }
            DynamicDetailBeanV2.ImagesBean task = tasks.get(i);
            int id = UIUtils.getResourceByName("siv_" + i, "id", getContext());
            ImageView imageView = holder.getView(id);
            ImageBean imageBean = new ImageBean();
            imageBean.setImgUrl(task.getImgUrl());
            Toll toll = new Toll();
            toll.setPaid(task.isPaid());
            toll.setToll_money(task.getAmount());
            toll.setToll_type_string(task.getType());
            toll.setPaid_node(task.getPaid_node());
            imageBean.setToll(toll);
            imageBean.setListCacheUrl(task.getGlideUrl());
            imageBean.setDynamicPosition(dynamicPosition);
            imageBean.setFeed_id(dynamicBean.getId());
            imageBean.setWidth(task.getWidth());
            imageBean.setHeight(task.getHeight());
            imageBean.setStorage_id(task.getFile());
            imageBean.setImgMimeType(task.getImgMimeType());
            imageBeanList.add(imageBean);
            AnimationRectBean rect = AnimationRectBean.buildFromImageView(imageView);
            animationRectBeanArrayList.add(rect);
        }

        GalleryActivity.startToGallery(getContext(), position, imageBeanList,
                animationRectBeanArrayList);
    }

    @Override
    public void share(int position) {
        position -= mHeaderAndFooterWrapper.getHeadersCount();
        if (mListDatas.get(position).getId() > 0) {
            Bitmap shareBitMap = getShareBitmap(position, R.id.thumb);
            mPresenter.shareDynamic(mListDatas.get(position), shareBitMap);
        }
    }

    @Override
    public void shareWihtType(int position, SHARE_MEDIA type) {
        position -= mHeaderAndFooterWrapper.getHeadersCount();
        if (mListDatas.get(position).getId() > 0) {
            mPresenter.shareDynamic(mListDatas.get(position), getShareBitmap(position, R.id.thumb),
                    type);
        }
    }

    @Override
    public void onMenuItemClick(View view, int dataPosition, int viewPosition) {
        dataPosition -= mHeaderAndFooterWrapper.getHeadersCount();// 减去 header
        mCurrentPostion = dataPosition;
        Bitmap shareBitMap = null;
        try {
            ImageView imageView = (ImageView) layoutManager.findViewByPosition(dataPosition + mHeaderAndFooterWrapper.getHeadersCount())
                    .findViewById(R.id.siv_0);
            shareBitMap = ConvertUtils.drawable2BitmapWithWhiteBg(getContext(), imageView.getDrawable(), R.mipmap.icon);
        } catch (Exception e) {
        }
        // 0 1 2 3 代表 view item 位置
        switch (viewPosition) {

            case 0:
                // 喜欢
                // 还未发送成功的动态列表不查看详情
                if (mListDatas.get(dataPosition).getId() == null || mListDatas.get(dataPosition).getId() == 0) {
                    return;
                }
                handleLike(dataPosition);
                break;

            case 1:
                // 评论
                // 还未发送成功的动态列表不查看详情
                if (mListDatas.get(dataPosition).getId() == null || mListDatas.get(dataPosition).getId() == 0) {
                    return;
                }
                showCommentView();
                mIlvComment.setEtContentHint(getString(R.string.default_input_hint));
                mCurrentPostion = dataPosition;
                // 0 代表评论动态
                mReplyToUserId = 0;
                break;

            case 2:
                // 浏览
                // 加上 header
                onItemClick(null, null, (dataPosition + mHeaderAndFooterWrapper.getHeadersCount()));
                break;

            case 3:
                // 更多
                initDeletDynamicPopupWindow(mListDatas.get(dataPosition), dataPosition, shareBitMap);
                break;
            default:
                // 加上 header
                onItemClick(null, null, (dataPosition + mHeaderAndFooterWrapper.getHeadersCount()));
        }
    }

    @Override
    public void refreshData() {
        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    @Override
    public void allDataReady() {
        closeLoadingView();
        mPersonalCenterHeaderViewItem.setViewColorWithAlpha(mLlToolbarContainerParent, STATUS_RGB, 0);
        mPersonalCenterHeaderViewItem.setViewColorWithAlpha(mLlToolbarContainerParent.findViewById(R.id.v_horizontal_line), TOOLBAR_DIVIDER_RGB, 0);
        mPersonalCenterHeaderViewItem.setToolbarIconColor(Color.argb(255, TOOLBAR_WHITE_ICON[0]
                , TOOLBAR_WHITE_ICON[1], TOOLBAR_WHITE_ICON[2]));
        mPersonalCenterHeaderViewItem.setScrollListenter();
        // 状态栏文字设为白色
        //StatusBarUtils.statusBarDarkMode(mActivity);
        initListener();
        // 进入页面尝试设置头部信息
        setHeaderInfo(mUserInfoBean);
    }

    @Override
    public void loadAllError() {
        showLoadViewLoadError();
    }

    @Override
    public void onSendClick(View v, String text) {
        DeviceUtils.hideSoftKeyboard(getContext(), v);
        mIlvComment.setVisibility(View.GONE);
        mVShadow.setVisibility(View.GONE);
        mPresenter.sendCommentV2(mCurrentPostion, mReplyToUserId, text);
    }

    @Override
    public void onAtTrigger() {
        AtUserActivity.startAtUserActivity(this);
    }

    @Override
    public void onReSendClick(int position) {
        // 去掉 header
        position = position - mHeaderAndFooterWrapper.getHeadersCount();
        initReSendDynamicPopupWindow(position);
        mReSendDynamicPopWindow.show();
    }


    @Override
    public void onItemClick(View view, final RecyclerView.ViewHolder holder, int position) {
        position = position - mHeaderAndFooterWrapper.getHeadersCount();
        mCurrentPostion = position;
        // 游客处理
        if (!TouristConfig.DYNAMIC_DETAIL_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }
        DynamicDetailBeanV2 detailBeanV2 = mListDatas.get(position);
        boolean canNotLookWords = detailBeanV2.getPaid_node() != null && !detailBeanV2.getPaid_node().isPaid()
                && detailBeanV2.getUser_id().intValue() != AppApplication.getmCurrentLoginAuth().getUser_id();
        if (canNotLookWords) {
            initImageCenterPopWindow(position, position, detailBeanV2.getPaid_node().getAmount(),
                    detailBeanV2.getPaid_node().getNode(), R.string.buy_pay_words_desc, false);
            return;
        }

        goDynamicDetail(position, false, (ViewHolder) holder);
    }

    @Override
    public void setSpanText(int position, int note, long amount, TextView view, boolean canNotRead) {
        position = position - mHeaderAndFooterWrapper.getHeadersCount();
        initImageCenterPopWindow(position, position, amount,
                note, R.string.buy_pay_words_desc, false);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @OnClick({R.id.iv_back, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.iv_more:
                showTopBarMorePopWindow();
                break;
            default:
        }
    }

    @Override
    public void setHeaderInfo(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            this.mUserInfoBean = userInfoBean;
            setBottomVisible(userInfoBean.getUser_id());
            mPersonalCenterHeaderViewItem.initHeaderViewData(userInfoBean, mDynamicType);
            // ui进行刷新
            setFollowState(userInfoBean);
        }
    }

    @Override
    public String getDynamicType() {
        return mDynamicType.value;
    }

    @Override
    public void onDynamicTypeChanged(BaseDynamicRepository.MyDynamicTypeEnum type) {
        if (type == mDynamicType) {
            return;
        }
        mDynamicType = type;
        requestNetData(DEFAULT_PAGE_MAX_ID, false);

    }

    @Override
    public void refreshStart() {
        mIvRefresh.setVisibility(View.VISIBLE);
        ((AnimationDrawable) mIvRefresh.getDrawable()).start();
    }

    @Override
    public void refreshEnd() {
        ((AnimationDrawable) mIvRefresh.getDrawable()).stop();
        mIvRefresh.setVisibility(View.GONE);
    }


    @Override
    public void setFollowState(UserInfoBean followFansBean) {
        mUserInfoBean = followFansBean;
        setBottomFollowState(followFansBean);
    }

    @Override
    public void setUpLoadCoverState(boolean upLoadState) {
        if (upLoadState) {
            // 封面图片上传成功
            // 通知服务器，更改用户信息
            // 修改成功后，关闭页面
            setChangeUserCoverState(true);
        } else {
            showSnackErrorMessage(getString(R.string.cover_uploadFailure));

        }
    }

    @Override
    public void setChangeUserCoverState(boolean changeSuccess) {
        if (changeSuccess) {
            showSnackSuccessMessage(getString(R.string.cover_change_success));
        } else {
            showSnackErrorMessage(getString(R.string.cover_change_failure));

        }
    }

    @Override
    public void getPhotoSuccess(List<ImageBean> photoList) {
        if (photoList.isEmpty()) {
            return;
        }
        // 选择图片完毕后，开始上传封面图片
        ImageBean imageBean = photoList.get(0);
        String imagePath = imageBean.getImgUrl();
        // 上传本地图片
        mPresenter.uploadUserCover(imagePath, mUserInfoBean);
        mUserInfoBean.setCover(new Avatar(imagePath));
        ImageUtils.updateCurrentLoginUserCoverSignature(getContext());
        // 加载本地图片
        mPersonalCenterHeaderViewItem.upDateUserCover(mUserInfoBean);
    }

    @Override
    public void getPhotoFailure(String errorMsg) {

    }

    @Override
    public void onCommentStateClick(DynamicCommentBean dynamicCommentBean, int position) {
        initReSendCommentPopupWindow(dynamicCommentBean, mListDatas.get(mPresenter.getCurrenPosiotnInDataList(dynamicCommentBean.getFeed_mark()))
                .getId());
        mReSendCommentPopWindow.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoSelector.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AtUserListFragment.REQUES_USER) {
            // @ 选人返回
            if (data != null && data.getExtras() != null) {
                UserInfoBean userInfoBean = data.getExtras().getParcelable(AtUserListFragment.AT_USER);
                if (userInfoBean != null) {
                    mIlvComment.appendAt(userInfoBean.getName());
                }
            }
            showComment = Observable.timer(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> showCommentView());
        }
    }

    @Override
    public void onCommentUserInfoClick(UserInfoBean userInfoBean) {
        onUserInfoClick(userInfoBean);
    }

    @Override
    public void onCommentContentClick(DynamicDetailBeanV2 dynamicBean, int position) {
        mCurrentPostion = mPresenter.getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
        if (dynamicBean.getComments().get(position).getUser_id() == AppApplication.getmCurrentLoginAuth().getUser_id()) {
            initDeletCommentPopWindow(dynamicBean, mCurrentPostion, position);
            mDeletCommentPopWindow.show();
        } else {
            showCommentView();
            mReplyToUserId = dynamicBean.getComments().get(position).getUser_id();
            String contentHint = getString(R.string.default_input_hint);
            if (dynamicBean.getComments().get(position).getReply_to_user_id() != dynamicBean.getUser_id()) {
                contentHint = getString(R.string.reply, dynamicBean.getComments().get(position).getCommentUser().getName());
            }
            mIlvComment.setEtContentHint(contentHint);
        }
    }

    @Override
    public void onCommentContentLongClick(DynamicDetailBeanV2 dynamicBean, int position) {
        if (!TouristConfig.DYNAMIC_CAN_COMMENT && mPresenter.handleTouristControl()) {
            return;
        }
        mCurrentPostion = mPresenter.getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
        // 举报
        if (dynamicBean.getComments().get(position).getUser_id() != AppApplication.getMyUserIdWithdefault()) {
            ReportActivity.startReportActivity(mActivity, new ReportResourceBean(dynamicBean.getComments().get
                    (position).getCommentUser(), dynamicBean.getComments().get
                    (position).getComment_id().toString(),
                    null, "", dynamicBean.getComments().get(position).getComment_content(), ReportType.COMMENT));

        } else {

        }
    }

    private void initToolBar() {
        if (!setUseStatusView()) {
            // toolBar 设置状态栏高度的 marginTop
            int height = getResources().getDimensionPixelSize(R.dimen.toolbar_height) + DeviceUtils.getStatuBarHeight(getContext()) + getResources
                    ().getDimensionPixelSize(R.dimen.divider_line);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mLlToolbarContainerParent.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置底部 view 的关注状态
     */
    private void setBottomFollowState(UserInfoBean userInfoBean1) {

        if (userInfoBean1.isFollowing() && userInfoBean1.isFollower()) {
            mTvFollow.setCompoundDrawables(UIUtils.getCompoundDrawables(getContext(), R.mipmap.ico_me_followed_eachother), null, null, null);
            mTvFollow.setTextColor(ContextCompat.getColor(getContext(), R.color.themeColor));
            mTvFollow.setText(R.string.followed_eachother);
        } else if (userInfoBean1.isFollower()) {
            mTvFollow.setCompoundDrawables(UIUtils.getCompoundDrawables(getContext(), R.mipmap.ico_me_followed), null, null, null);
            mTvFollow.setTextColor(ContextCompat.getColor(getContext(), R.color.themeColor));
            mTvFollow.setText(R.string.followed);
        } else {
            mTvFollow.setCompoundDrawables(UIUtils.getCompoundDrawables(getContext(), R.mipmap.ico_me_follow), null, null, null);
            mTvFollow.setTextColor(ContextCompat.getColor(getContext(), R.color.important_for_content));
            mTvFollow.setText(R.string.follow);
        }
        mLlFollowContainer.setEnabled(true);
    }


    /**
     * 设置底部 view 的可见性;如果进入了当前登录用户的主页，需要隐藏底部状态栏
     *
     * @param currentUserID
     */
    private void setBottomVisible(long currentUserID) {
        AuthBean authBean = AppApplication.getmCurrentLoginAuth();
        mLlBottomContainer.setVisibility((authBean != null && authBean.getUser_id() == currentUserID) ? View.GONE : View.VISIBLE);
    }


    private void setAdapter(MultiItemTypeAdapter adapter, DynamicListBaseItem dynamicListBaseItem) {
        dynamicListBaseItem.setOnImageClickListener(this);
        dynamicListBaseItem.setOnUserInfoClickListener(this);
        dynamicListBaseItem.setOnMenuItemClickLisitener(this);
        dynamicListBaseItem.setOnReSendClickListener(this);
        dynamicListBaseItem.setOnMoreCommentClickListener(this);
        dynamicListBaseItem.setOnCommentClickListener(this);
        dynamicListBaseItem.setOnCommentStateClickListener(this);
        dynamicListBaseItem.setOnSpanTextClickListener(this);
        adapter.addItemViewDelegate(dynamicListBaseItem);
    }


    /**
     * 喜欢
     *
     * @param dataPosition
     */
    private void handleLike(int dataPosition) {
        // 先更新界面，再后台处理
        mListDatas.get(dataPosition).setHas_digg(!mListDatas.get(dataPosition)
                .isHas_digg());
        mListDatas.get(dataPosition).setFeed_digg_count(mListDatas.get(dataPosition)
                .isHas_digg() ?
                mListDatas.get(dataPosition).getFeed_digg_count() + 1 : mListDatas.get
                (dataPosition).getFeed_digg_count() - 1);
        refreshData();
        mPresenter.handleLike(mListDatas.get(dataPosition).isHas_digg(),
                mListDatas.get(dataPosition).getId(), dataPosition);
    }

    private void showCommentView() {
        // 评论
        mIlvComment.setVisibility(View.VISIBLE);
        mIlvComment.setSendButtonVisiable(true);
        mIlvComment.getFocus();
        mVShadow.setVisibility(View.VISIBLE);
        DeviceUtils.showSoftKeyboard(getActivity(), mIlvComment.getEtContent());

//        showInputPsdView(true);
    }


    private void goDynamicDetail(int position, boolean isLookMoreComment, ViewHolder holder) {
        // 还未发送成功的动态列表不查看详情
        if (mListDatas.get(position).getId() == null || mListDatas.get(position).getId() == 0) {
            return;
        }
        mPresenter.handleViewCount(mListDatas.get(position).getId(), position);
        Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DYNAMIC_DETAIL_DATA, mListDatas.get(position));
        bundle.putInt(DYNAMIC_DETAIL_DATA_POSITION, position);
        bundle.putBoolean(LOOK_COMMENT_MORE, isLookMoreComment);

        if (isLookMoreComment) {
            ZhiyiVideoView.releaseAllVideos();
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }

        ZhiyiVideoView playView = null;
        try {
            playView = holder.getView(R.id.videoplayer);
        } catch (Exception ignore) {
            LogUtils.e(ignore.getMessage());
        }

        if (playView != null && JZVideoPlayerManager.getFirstFloor() != null) {
            playView.mVideoFrom = VIDEO_FROME;
            if (playView.currentState == ZhiyiVideoView.CURRENT_STATE_PLAYING) {
                playView.startButton.callOnClick();
            }
            bundle.putInt(DYNAMIC_VIDEO_STATE, playView.currentState);
            playView.textureViewContainer.removeView(JZMediaManager.textureView);
            playView.onStateNormal();
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mListDatas.isEmpty()) {
            refreshData();
        }
    }

    /**
     * 初始化评论删除选择弹框
     *
     * @param dynamicBean     curent dynamic
     * @param dynamicPositon  dynamic comment position
     * @param commentPosition current comment position
     */
    private void initDeletCommentPopWindow(final DynamicDetailBeanV2 dynamicBean, final int dynamicPositon, final int commentPosition) {
        mDeletCommentPopWindow = ActionPopupWindow.builder()
                .item1Str(BuildConfig.USE_TOLL && dynamicBean.getState() == DynamicDetailBeanV2
                        .SEND_SUCCESS && !dynamicBean
                        .getComments().get(commentPosition).getPinned() && dynamicBean.getComments().get(commentPosition).getComment_id() != null ?
                        getString(R
                                .string.dynamic_list_top_comment) : null)
                .item2Str(getString(R.string.dynamic_list_delete_comment))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(getActivity())
                .item1ClickListener(() -> {
                    boolean sourceIsMine = AppApplication.getMyUserIdWithdefault() == dynamicBean.getUser_id();

                    StickTopFragment.startSticTopActivity(getActivity(), StickTopFragment.TYPE_DYNAMIC, dynamicBean.getId(), dynamicBean
                            .getComments().get(commentPosition).getComment_id(), sourceIsMine);
                    mDeletCommentPopWindow.hide();
                })
                .item2ClickListener(() -> {
                    mDeletCommentPopWindow.hide();
                    showDeleteTipPopupWindow(getString(R.string.delete_comment), () -> {
                        mPresenter.deleteCommentV2(dynamicBean, dynamicPositon, dynamicBean.getComments().get(commentPosition).getComment_id() !=
                                        null ? dynamicBean.getComments().get(commentPosition).getComment_id() : 0,
                                commentPosition);
                    }, true);

                })
                .bottomClickListener(() -> mDeletCommentPopWindow.hide())
                .build();
    }


    /**
     * 顶部更多弹框
     */
    private void showTopBarMorePopWindow() {
        boolean isBlackMan = mUserInfoBean.getBlacked();
        boolean feedIsMy = mUserInfoBean.getUser_id() == AppApplication.getMyUserIdWithdefault();
        mTopBarMorePopWindow = ActionPopupWindow.builder()
                .item1Str(getString(R.string.share))
                .item2Str(feedIsMy ? "" : getString(R.string.report))
                .item3Str(feedIsMy ? "" : getString(isBlackMan ? R.string.remove_black_list : R.string.add_to_black_list))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(getActivity())
                .item1ClickListener(() -> {
                    mPresenter.shareUserInfo(mUserInfoBean);
                    mTopBarMorePopWindow.hide();
                })
                .item2ClickListener(() -> {
                    ReportActivity.startReportActivity(mActivity, new ReportResourceBean(mUserInfoBean, mUserInfoBean.getUser_id().toString(),
                            mUserInfoBean
                                    .getName(), mUserInfoBean.getAvatar(), mUserInfoBean.getIntro(), ReportType.USER));
                    mTopBarMorePopWindow.hide();
                })
                .item3ClickListener(() -> {
                    mTopBarMorePopWindow.hide();
                    if (isBlackMan) {
                        mPresenter.removeBlackLIst(mUserInfoBean);
                    } else {
                        mPresenter.addToBlackList(mUserInfoBean);
                    }
                })
                .bottomClickListener(() -> mTopBarMorePopWindow.hide())
                .build();
        mTopBarMorePopWindow.show();
    }

    @Override
    public void updateUserBlackStatus(boolean b) {
        if (mUserInfoBean != null) {
            mUserInfoBean.setBlacked(b);
        }
    }

    @Override
    public void showDeleteTipPopupWindow(DynamicDetailBeanV2 detailBeanV2) {
        showDeleteTipPopupWindow(getString(R.string.dynamic_list_delete_dynamic), ()
                -> mPresenter.deleteDynamic(detailBeanV2, 0), true);
    }

    /**
     * 初始化动态删除选择弹框
     *
     * @param dynamicBean curent dynamic
     * @param position    curent dynamic postion
     */
    private void initDeletDynamicPopupWindow(final DynamicDetailBeanV2 dynamicBean, final int position, final Bitmap shareBitmap) {
        boolean isCollected = dynamicBean.isHas_collect();
        boolean feedIsMy = dynamicBean.getUser_id().intValue() == AppApplication.getmCurrentLoginAuth().getUser_id();
        boolean hasDynamicDeletePermission = AppApplication.getmCurrentLoginAuth().getUserPermissionIds() != null &&
                AppApplication.getmCurrentLoginAuth().getUserPermissionIds().contains(UserPermissions
                        .FEED_DELETE.value);


        boolean isTop = dynamicBean.getTop() == DynamicDetailBeanV2.TOP_SUCCESS;

        List<UmengSharePolicyImpl.ShareBean> data;
        UmengSharePolicyImpl.ShareBean forward = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_forwarding, getString(R.string
                .share_forward), Share.FORWARD);
        UmengSharePolicyImpl.ShareBean sticktop = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_top, getString(R.string.share_sticktp),
                Share.STICKTOP);
        UmengSharePolicyImpl.ShareBean collect = new UmengSharePolicyImpl.ShareBean(isCollected ? R.mipmap.detail_share_clt_hl : R.mipmap
                .detail_share_clt, getString(isCollected ? R.string.dynamic_list_collected_dynamic : R
                .string.dynamic_list_collect_dynamic), Share.COLLECT);
        UmengSharePolicyImpl.ShareBean letter = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_sent, getString(R.string.share_letter),
                Share.LETTER);
        UmengSharePolicyImpl.ShareBean delete = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_det, getString(R.string.share_delete),
                Share.DELETE);
        UmengSharePolicyImpl.ShareBean report = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_report, getString(R.string.share_report),
                Share.REPORT);

        data = new ArrayList<>();
        data.add(forward);
        data.add(letter);
        if (!feedIsMy && !hasDynamicDeletePermission) {
            data.add(report);
        }
        data.add(collect);
        if (!isTop && feedIsMy) {
            data.add(sticktop);
        }
        if (feedIsMy || hasDynamicDeletePermission) {
            data.add(delete);
        }
        mPresenter.shareDynamic(dynamicBean, shareBitmap, data);
    }

    @Override
    public void updateDynamicCounts(int changeNums) {
        rx.Observable.just(changeNums)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    int currenDynamicCounts = mUserInfoBean.getExtra().getFeeds_count();
                    currenDynamicCounts += integer;
                    if (currenDynamicCounts < 0) {
                        currenDynamicCounts = 0;
                    }
                    mUserInfoBean.getExtra().setFeeds_count(currenDynamicCounts);
                    mPersonalCenterHeaderViewItem.upDateDynamicNums(currenDynamicCounts);
                }, Throwable::printStackTrace);

    }

    @Override
    public Bitmap getUserHeadPic() {
        if (mPersonalCenterHeaderViewItem.getHeadView() == null) {
            return null;
        }
        return ConvertUtils.drawable2BitmapWithWhiteBg(getContext(), mPersonalCenterHeaderViewItem.getHeadView().getDrawable(), R.mipmap.icon);
    }

    /**
     * 初始化重发评论选择弹框
     */
    private void initReSendCommentPopupWindow(final DynamicCommentBean commentBean, final long feed_id) {
        mReSendCommentPopWindow = ActionPopupWindow.builder()
                .item1Str(getString(R.string.dynamic_list_resend_comment))
                .item1Color(ContextCompat.getColor(getContext(), R.color.themeColor))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(getActivity())
                .item1ClickListener(() -> {
                    mReSendCommentPopWindow.hide();
                    mPresenter.reSendComment(commentBean, feed_id);
                })
                .bottomClickListener(() -> mReSendCommentPopWindow.hide())
                .build();
    }

    /**
     * 初始化重发动态选择弹框
     */
    private void initReSendDynamicPopupWindow(final int position) {
        mReSendDynamicPopWindow = ActionPopupWindow.builder()
                .item1Str(getString(R.string.resend))
                .item1Color(ContextCompat.getColor(getContext(), R.color.themeColor))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(getActivity())
                .item1ClickListener(() -> {
                    mReSendDynamicPopWindow.hide();
                    mListDatas.get(position).setState(DynamicDetailBeanV2.SEND_ING);
                    refreshData();
                    mPresenter.reSendDynamic(position);
                })
                .bottomClickListener(() -> mReSendDynamicPopWindow.hide())
                .build();
    }

    @Override
    public void onMoreCommentClick(View view, DynamicDetailBeanV2 dynamicBean) {
        int position = mPresenter.getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
        goDynamicDetail(position, true, (ViewHolder) mRvList.findViewHolderForAdapterPosition(position));
    }

    @Override
    public void onNetResponseSuccess(@NotNull List<DynamicDetailBeanV2> data, boolean isLoadMore) {
        if (!isLoadMore && data.isEmpty()) { // 增加空数据，用于显示占位图
            DynamicDetailBeanV2 emptyData = new DynamicDetailBeanV2();
            data.add(emptyData);
        }
        super.onNetResponseSuccess(data, isLoadMore);
        if (!isLoadMore) {
            refreshEnd();
        }
    }

    /**
     * @param dynamicPosition 动态位置
     * @param imagePosition   图片位置
     * @param amout           费用
     * @param note            支付节点
     * @param strRes          文字说明
     * @param isImage         是否是图片收费
     */
    private void initImageCenterPopWindow(final int dynamicPosition, final int imagePosition, long amout,
                                          final int note, int strRes, final boolean isImage) {

        mPayImagePopWindow = PayPopWindow.builder()
                .with(getActivity())
                .isWrap(true)
                .isFocus(true)
                .isOutsideTouch(true)
                .buildLinksColor1(R.color.themeColor)
                .buildLinksColor2(R.color.important_for_content)
                .contentView(R.layout.ppw_for_center)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .buildDescrStr(String.format(getString(strRes) + getString(R
                                .string.buy_pay_member), amout,
                        mPresenter.getGoldName()))
                .buildLinksStr(getString(R.string.buy_pay_member))
                .buildTitleStr(getString(R.string.buy_pay))
                .buildItem1Str(getString(R.string.buy_pay_in))
                .buildItem2Str(getString(R.string.buy_pay_out))
                .buildMoneyStr(getString(R.string.buy_pay_integration, "" + amout))
                .buildCenterPopWindowItem1ClickListener(() -> {
                    if (mPresenter.usePayPassword()) {
                        mIlvPassword.setPayNote(new InputPasswordView.PayNote(dynamicPosition, imagePosition, note, isImage, null));
                        showInputPsdView(true);
                    } else {
                        mPresenter.payNote(dynamicPosition, imagePosition, note, isImage, null);
                    }
                    mPayImagePopWindow.hide();
                })
                .buildCenterPopWindowItem2ClickListener(() -> mPayImagePopWindow.hide())
                .buildCenterPopWindowLinkClickListener(new PayPopWindow
                        .CenterPopWindowLinkClickListener() {
                    @Override
                    public void onLongClick() {

                    }

                    @Override
                    public void onClicked() {

                    }
                })
                .build();
        mPayImagePopWindow.show();

    }

    @Override
    protected void onShadowViewClick() {
        mIlvComment.setVisibility(View.GONE);
        mIlvComment.clearFocus();
        DeviceUtils.hideSoftKeyboard(getActivity(), mIlvComment.getEtContent());
        showInputPsdView(false);
    }

    private Bitmap getShareBitmap(int position, int id) {
        Bitmap shareBitMap = null;
        try {
            ImageView imageView = (ImageView) layoutManager.findViewByPosition
                    (position + mHeaderAndFooterWrapper.getHeadersCount()).findViewById(id);
            shareBitMap = ConvertUtils.drawable2BitmapWithWhiteBg(getContext(), imageView
                    .getDrawable(), R.mipmap.icon);
        } catch (Exception e) {
        }
        return shareBitMap;
    }


    @Override
    public void onBackPressed() {
        if (mIlvComment.getVisibility() == View.GONE) {
            getActivity().finish();
        } else {
            onShadowViewClick();
        }
    }

    @Override
    public void onDestroyView() {
        dismissPop(mPayImagePopWindow);
        dismissPop(mDeletCommentPopWindow);
        dismissPop(mDeletDynamicPopWindow);
        dismissPop(mReSendCommentPopWindow);
        dismissPop(mReSendDynamicPopWindow);
        dismissPop(mTopBarMorePopWindow);
        if (showComment != null && !showComment.isUnsubscribed()) {
            showComment.unsubscribe();
        }
        super.onDestroyView();
    }
}
