package com.qinghua.demo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.qinghua.demo.R;
import com.qinghua.demo.utils.CommonFileUtil;
import com.qinghua.demo.utils.ToastUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

@Route(path = "/second/glide_demo")
public class GlideDemoActivity extends AppCompatActivity {

    private static final String imagePath = "https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png";
    @BindView(R.id.image)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //自已切换布局文件看效果
        setContentView(R.layout.activity_glide_demo);
        ButterKnife.bind(this);
        Glide.with(this).load(imagePath).into(mImageView);
    }

    @OnClick({R.id.download, R.id.gif})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.download:
                downloadPic(imagePath);
                break;
            case R.id.gif:
                // 加不加asGif都一样
//                Glide.with(this).asGif().load(R.mipmap.voice_gif).into(mImageView);
                Glide.with(this).load(R.mipmap.voice_gif).into(mImageView);
                break;
            default:
                break;
        }
    }

    /**
     * 保存图片
     * @param imagePath
     */
    private void downloadPic2(String imagePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = Glide.with(GlideDemoActivity.this)
                            .asFile()
                            .load(imagePath)
                            .submit()
                            .get();
                    Log.e("GlideDemoActivity", "path-->" + file.getPath());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 下载图片
     * 这里要申请下动态权限
     * @param url
     */
    private void downloadPic(String url) {
        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                try {
//                    FutureTarget<File> future = Glide
//                            .with(getActivity())
//                            .load(url)
//                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    file = Glide.with(GlideDemoActivity.this)
                            .asFile()
                            .load(url)
                            .submit()
                            .get();
                    Log.e("GlideDemoActivity", "path-->" + file.getPath());
//                    file = future.get();

                    // 首先保存图片
                    String pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();

//                    File appDir = new File(pictureFolder ,"Beauty");
//                    if (!appDir.exists()) {
//                        appDir.mkdirs();
//                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(pictureFolder, fileName);

                    CommonFileUtil.copyFile(file, pictureFolder, fileName);

                    // 最后通知图库更新
                    GlideDemoActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {
                ToastUtil.toastShortShow(GlideDemoActivity.this, "图片已保存到相册");
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }
}
