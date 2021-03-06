package com.zhiyicx.thinksnsplus.modules.music_fm.music_comment;

import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.common.base.BaseJson;
import com.zhiyicx.thinksnsplus.data.beans.MusicCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.MusicAlbumDetailsBean;
import com.zhiyicx.thinksnsplus.data.beans.MusicCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.MusicDetaisBean;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IMusicRepository;
import com.zhiyicx.thinksnsplus.service.backgroundtask.BackgroundTaskHandler;

import java.util.List;

import rx.Observable;

/**
 * @Author Jliuer
 * @Date 2017/03/22
 * @Email Jliuer@aliyun.com
 * @Description
 */
public interface MusicCommentContract {

    interface View extends ITSListView<MusicCommentListBean,Presenter>{
        String getType();
        long getCommentId();
        void setHeaderInfo(MusicCommentHeader.HeaderInfo headerInfo);
    }

    interface Presenter extends ITSListPresenter<MusicCommentListBean>{
        void requestNetData(String music_id, Long maxId, boolean isLoadMore);
        void sendComment(long reply_id, String content);

        void deleteComment(MusicCommentListBean data);
        void reSendComment(MusicCommentListBean data);
        void getMusicDetails(String music_id);
        void getMusicAblum(String id);
    }

}
