package com.zhiyicx.thinksnsplus.modules.findsomeone.search.name;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.source.repository.UserInfoRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/8/7
 * @Contact master.jungle68@gmail.com
 */

public class SearchSomeOnePresenter extends AppBasePresenter<SearchSomeOneContract.View>
        implements SearchSomeOneContract.Presenter {

    UserInfoRepository mUserInfoRepository;
    private Subscription searchSub;

    private String mSeachText;

    private List<UserInfoBean> mRecommentUserList = new ArrayList<>();
    private List<UserInfoBean> mFollowUserList = new ArrayList<>();


    @Inject
    public SearchSomeOnePresenter(SearchSomeOneContract.View rootView, UserInfoRepository userInfoRepository) {
        super(rootView);
        mUserInfoRepository = userInfoRepository;
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        if (searchSub != null && !searchSub.isUnsubscribed()) {
            searchSub.unsubscribe();
        }

        searchSub = mUserInfoRepository.searchUserInfo(null, mSeachText, maxId.intValue(), null, TSListFragment.DEFAULT_PAGE_SIZE)
                .subscribe(new BaseSubscribeForV2<List<UserInfoBean>>() {
                    @Override
                    protected void onSuccess(List<UserInfoBean> data) {

                        mRootView.onNetResponseSuccess(data, isLoadMore);
                    }

                    @Override
                    protected void onFailure(String message, int code) {
                        super.onFailure(message, code);
                        mRootView.onResponseError(null, isLoadMore);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                        mRootView.onResponseError(throwable, isLoadMore);
                    }
                });
        addSubscrebe(searchSub);

    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {
        mRootView.onCacheResponseSuccess(new ArrayList<>(), isLoadMore);
    }

    @Override
    public boolean insertOrUpdateData(@NotNull List<UserInfoBean> data, boolean isLoadMore) {
        return false;
    }

    @Override
    public void searchUser(String name) {
        mSeachText = name;
        requestNetData(0L, false);
    }

    @Override
    public void getRecommentUser() {
        if (mRecommentUserList != null && !mRecommentUserList.isEmpty()) {
            mRootView.onNetResponseSuccess(mRecommentUserList, false);
            return;
        }
        Subscription subscribe = mUserInfoRepository.getRecommendUserInfo()
                .subscribe(new BaseSubscribeForV2<List<UserInfoBean>>() {
                    @Override
                    protected void onSuccess(List<UserInfoBean> data) {
                        mRecommentUserList = data;
                        mRootView.onNetResponseSuccess(data, false);
                    }

                    @Override
                    protected void onFailure(String message, int code) {
                        super.onFailure(message, code);
                        mRootView.onResponseError(null, false);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                        mRootView.onResponseError(throwable, false);
                    }
                });
        addSubscrebe(subscribe);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore, int pageType) {

    }


    @Override
    public void followUser(int index, UserInfoBean followFansBean) {
        mUserInfoRepository.handleFollow(followFansBean);
        mRootView.upDateFollowFansState(index);

    }

    @Override
    public void cancleFollowUser(int index, UserInfoBean followFansBean) {
        mUserInfoRepository.handleFollow(followFansBean);
        mRootView.upDateFollowFansState(index);
    }

}
