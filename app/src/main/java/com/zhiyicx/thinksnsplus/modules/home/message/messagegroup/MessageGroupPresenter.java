package com.zhiyicx.thinksnsplus.modules.home.message.messagegroup;

import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.ChatGroupBeanForGroupList;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseMessageRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Author Jliuer
 * @Date 2018/05/03/15:47
 * @Email Jliuer@aliyun.com
 * @Description
 */
@FragmentScoped
public class MessageGroupPresenter extends AppBasePresenter<MessageGroupContract.View>
        implements MessageGroupContract.Presenter {

    @Inject
    BaseMessageRepository mBaseMessageRepository;

    private Subscription mGroupExistSubscription;

    @Inject
    public MessageGroupPresenter(MessageGroupContract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        // 搜索时的刷新，只处理本地数据
        if (!TextUtils.isEmpty(mRootView.getsearchKeyWord())) {
            mRootView.hideRefreshState(isLoadMore);
            return;
        }
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            Subscription subscribe = Observable.just(maxId.intValue())
                    .subscribeOn(Schedulers.io())
                    .flatMap((Func1<Integer, Observable<List<ChatGroupBeanForGroupList>>>) integer -> {
                        try {
                            List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer(mRootView.getPage(), integer);
                            if (grouplist != null && !grouplist.isEmpty()) {
                                StringBuilder builder = new StringBuilder();
                                for (EMGroup group : grouplist) {
                                    builder.append(group.getGroupId());
                                    builder.append(",");
                                }
                                return mBaseMessageRepository.getGroupInfoOnlyGroupFace(builder.toString());
                            }
                            return Observable.just(new ArrayList<>());
                        } catch (HyphenateException e) {
                            return Observable.just(new ArrayList<>());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscribeForV2<List<ChatGroupBeanForGroupList>>() {
                        @Override
                        protected void onSuccess(List<ChatGroupBeanForGroupList> data) {
                            mRootView.onNetResponseSuccess(data, isLoadMore);
                        }

                        @Override
                        protected void onFailure(String message, int code) {
                            super.onFailure(message, code);
                            mRootView.showStickyMessage(message);
                            mRootView.onResponseError(null, false);
                        }

                        @Override
                        protected void onException(Throwable throwable) {
                            super.onException(throwable);
                            mRootView.showStickyMessage(mContext.getString(R.string.chat_unconnected));
                            mRootView.onResponseError(throwable, false);
                        }
                    });
            addSubscrebe(subscribe);
        } else {
            mRootView.showStickyMessage(mContext.getString(R.string.chat_unconnected));
            mRootView.onResponseError(null, false);
        }


    }

    @Override
    public void checkGroupExist(ChatGroupBeanForGroupList groupBean) {
        if (mGroupExistSubscription != null && !mGroupExistSubscription.isUnsubscribed()) {
            mGroupExistSubscription.unsubscribe();
        }
        mGroupExistSubscription = Observable.just(groupBean.getId())
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    EMGroup group = null;
                    try {
                        group = EMClient.getInstance().groupManager().getGroupFromServer(s);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    return group;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribeForV2<EMGroup>() {
                    @Override
                    protected void onSuccess(EMGroup data) {
                        mRootView.checkGroupExist(groupBean, data);
                    }
                });
        addSubscrebe(mGroupExistSubscription);

    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {

    }

    @Override
    public boolean insertOrUpdateData(@NotNull List<ChatGroupBeanForGroupList> data, boolean isLoadMore) {
        mBaseMessageRepository.saveChatGoupForGroupList(data);
        return false;
    }
}
