package com.zycx.shortvideo.filter.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.zycx.shortvideo.filter.advanced.MagicBeautyFilter;
import com.zycx.shortvideo.filter.base.GPUImageFilter;
import com.zycx.shortvideo.filter.base.avfilter.AFilter;
import com.zycx.shortvideo.filter.base.avfilter.GroupFilter;
import com.zycx.shortvideo.filter.base.avfilter.NoFilter;
import com.zycx.shortvideo.filter.base.avfilter.ProcessFilter;
import com.zycx.shortvideo.filter.base.avfilter.RotationOESFilter;
import com.zycx.shortvideo.filter.base.avfilter.WaterMarkFilter;
import com.zycx.shortvideo.filter.helper.type.GlUtil;
import com.zycx.shortvideo.interfaces.SingleCallback;
import com.zycx.shortvideo.media.VideoInfo;
import com.zycx.shortvideo.utils.MatrixUtils;
import com.zycx.video.R;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by cj on 2017/10/16.
 * desc：添加水印和美白效果
 */

public class VideoDrawer implements GLSurfaceView.Renderer {
    /**
     * 用于后台绘制的变换矩阵
     */
    private float[] OM;
    /**
     * 用于显示的变换矩阵
     */
    private float[] SM = new float[16];
    private SurfaceTexture surfaceTexture;
    /**
     * 可选择画面的滤镜
     */
    private RotationOESFilter mPreFilter;
    /**
     * 显示的滤镜
     */
    private AFilter mShow;
    /**
     * 美白的filter
     */
    private MagicBeautyFilter mBeautyFilter;
    private AFilter mProcessFilter;
    /**
     * 绘制水印的滤镜
     */
    private final GroupFilter mBeFilter;
    /**
     * 多种滤镜切换
     */
    private SlideGpuFilterGroup mSlideFilterGroup;

    /**
     * 绘制其他样式的滤镜
     */
    private GPUImageFilter mGroupFilter;
    /**
     * 控件的长宽
     */
    private int viewWidth;
    private int viewHeight;

    /**
     * 创建离屏buffer
     */
    private int[] fFrame = new int[1];
    private int[] fTexture = new int[1];
    /**
     * 用于视频旋转的参数
     */
    private int rotation;
    /**
     * 是否开启美颜
     */
    private boolean isBeauty = false;
    private boolean isTakePic = false;
    private SingleCallback<Bitmap, Integer> mSingleCallback;


    public VideoDrawer(Context context, Resources res) {
        mPreFilter = new RotationOESFilter(res);//旋转相机操作
        mShow = new NoFilter(res);
        mBeFilter = new GroupFilter(res);
        mBeautyFilter = new MagicBeautyFilter();

        mProcessFilter = new ProcessFilter(res);
        mSlideFilterGroup = new SlideGpuFilterGroup();
        OM = MatrixUtils.getOriginalMatrix();
        MatrixUtils.flip(OM, false, true);//矩阵上下翻转
//        mShow.setMatrix(OM);

//        WaterMarkFilter waterMarkFilter = new WaterMarkFilter(res);
//        waterMarkFilter.setWaterMark(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round));
//
//        waterMarkFilter.setPosition(0, 70, 0, 0);
        mBeFilter.addFilter(mShow);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int texture[] = new int[1];
        GLES30.glGenTextures(1, texture, 0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

        surfaceTexture = new SurfaceTexture(texture[0]);
        mPreFilter.create();
        mPreFilter.setTextureId(texture[0]);

        mBeFilter.create();
        mProcessFilter.create();
        mShow.create();
        mBeautyFilter.init();
        mBeautyFilter.setBeautyLevel(3);//默认设置3级的美颜
        mSlideFilterGroup.init();
    }

    public void onVideoChanged(VideoInfo info) {
        setRotation(info.rotation);
        if (info.rotation == 0 || info.rotation == 180) {
            MatrixUtils.getShowMatrix(SM, info.width, info.height, viewWidth, viewHeight);
        } else {
            MatrixUtils.getShowMatrix(SM, info.height, info.width, viewWidth, viewHeight);
        }

        mPreFilter.setMatrix(SM);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        viewWidth = width;
        viewHeight = height;
        GLES30.glDeleteFramebuffers(1, fFrame, 0);
        GLES30.glDeleteTextures(1, fTexture, 0);

        GLES30.glGenFramebuffers(1, fFrame, 0);
        GlUtil.genTexturesWithParameter(1, fTexture, 0, GLES30.GL_RGBA, viewWidth, viewHeight);

        mBeFilter.setSize(viewWidth, viewHeight);
        mProcessFilter.setSize(viewWidth, viewHeight);
        mBeautyFilter.onDisplaySizeChanged(viewWidth, viewHeight);
        mBeautyFilter.onInputSizeChanged(viewWidth, viewHeight);
        mSlideFilterGroup.onSizeChanged(viewWidth, viewHeight);
    }

    @Override
    public void onDrawFrame(final GL10 gl) {
        surfaceTexture.updateTexImage();
        GlUtil.bindFrameTexture(fFrame[0], fTexture[0]);
        GLES30.glViewport(0, 0, viewWidth, viewHeight);
        mPreFilter.draw();
        GlUtil.unBindFrameBuffer();

        mBeFilter.setTextureId(fTexture[0]);
        mBeFilter.draw();

        if (mBeautyFilter != null && isBeauty && mBeautyFilter.getBeautyLevel() != 0) {
            GlUtil.bindFrameTexture(fFrame[0], fTexture[0]);
            GLES30.glViewport(0, 0, viewWidth, viewHeight);
            mBeautyFilter.onDrawFrame(mBeFilter.getOutputTexture());
            GlUtil.unBindFrameBuffer();
            mProcessFilter.setTextureId(fTexture[0]);
        } else {
            mProcessFilter.setTextureId(mBeFilter.getOutputTexture());
        }
        mProcessFilter.draw();

        mSlideFilterGroup.onDrawFrame(mProcessFilter.getOutputTexture());
        if (mGroupFilter != null) {
            GlUtil.bindFrameTexture(fFrame[0], fTexture[0]);
            GLES30.glViewport(0, 0, viewWidth, viewHeight);
            mGroupFilter.onDrawFrame(mSlideFilterGroup.getOutputTexture());
            GlUtil.unBindFrameBuffer();
            mProcessFilter.setTextureId(fTexture[0]);
        } else {
            mProcessFilter.setTextureId(mSlideFilterGroup.getOutputTexture());
        }
        mProcessFilter.draw();

        GLES30.glViewport(0, 0, viewWidth, viewHeight);

        mShow.setTextureId(mProcessFilter.getOutputTexture());
        mShow.draw();

        if (isTakePic) {
            isTakePic = false;
            mSingleCallback.onSingleCallback(createBitmapFromGLSurface(0, 0, viewWidth, viewHeight, gl), 1);
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return surfaceTexture;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        if (mPreFilter != null) {
            mPreFilter.setRotation(this.rotation);
        }
    }

    /**
     * 切换开启美白效果
     */
    public void switchBeauty() {
        isBeauty = !isBeauty;
    }

    /**
     * 是否开启美颜功能
     */
    public void isOpenBeauty(boolean isBeauty) {
        this.isBeauty = isBeauty;
    }

    /**
     * 触摸事件监听
     */
    public void onTouch(MotionEvent event) {
        mSlideFilterGroup.onTouchEvent(event);
    }

    /**
     * 滤镜切换的监听
     */
    public void setOnFilterChangeListener(SlideGpuFilterGroup.OnFilterChangeListener listener) {
        mSlideFilterGroup.setOnFilterChangeListener(listener);
    }

    public void checkGlError(String s) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            throw new RuntimeException(s + ": glError " + error);
        }
    }

    public void setGpuFilter(GPUImageFilter filter) {
        if (filter != null) {
            mGroupFilter = filter;
            mGroupFilter.init();
            mGroupFilter.onDisplaySizeChanged(viewWidth, viewWidth);
            mGroupFilter.onInputSizeChanged(viewWidth, viewHeight);
        }
    }

    public void takePic(SingleCallback<Bitmap, Integer> singleCallback) {
        mSingleCallback = singleCallback;
        isTakePic = true;
    }

    private Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl) {
        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);
        try {
            gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE,
                    intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            return null;
        }
        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }
}
