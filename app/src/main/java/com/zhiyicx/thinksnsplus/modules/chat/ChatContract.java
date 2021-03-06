package com.zhiyicx.thinksnsplus.modules.chat;

import com.hyphenate.chat.EMMessage;
import com.zhiyicx.baseproject.em.manager.eventbus.TSEMRefreshEvent;
import com.zhiyicx.common.mvp.i.IBasePresenter;
import com.zhiyicx.common.mvp.i.IBaseView;
import com.zhiyicx.thinksnsplus.data.beans.ChatGroupBean;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;

import java.util.List;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/1/6
 * @Contact master.jungle68@gmail.com
 */

public interface ChatContract {

    interface View extends IBaseView<Presenter> {
        void onMessageReceivedWithUserInfo(List<EMMessage> messages);
        void setGoupName(String name);

        /**
         * 更新标题
         * @param s
         */
        void setTitle(String s);

        /**
         * 通知类消息的用户信息
         * @param
         */
        void updateUserInfoForRefreshList(UserInfoBean data,TSEMRefreshEvent event);

        void updateCenterText(UserInfoBean userInfoBean);

        void gotoCircleDetail(CircleInfo data,Long postId);
    }

    interface Presenter extends IBasePresenter {
        void dealMessages(List<EMMessage> messages);
        String getUserName(String id);
        void getCircleInfo(Long circleId,Long postId);

        /**
         * 从本地拿
         * @param id
         * @return
         */
        ChatGroupBean getChatGroupInfo(String id);

        /**
         * 从服务器拿
         * @param groupId
         */
        void getGroupChatInfo(String groupId);
        void updateGroupName(ChatGroupBean chatGroupBean);

        void getUserInfoForRefreshList(TSEMRefreshEvent event);

        String getGroupName(String id);
        boolean updateChatGroupMemberCount(String id,int count,boolean add);

        String getCurrenLoginUserName(long myUserIdWithdefault);
    }
}
