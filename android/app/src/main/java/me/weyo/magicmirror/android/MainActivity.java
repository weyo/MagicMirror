package me.weyo.magicmirror.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.speech.util.JsonParser;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.weyo.magicmirror.android.service.HttpRequest;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();

    private Toast mToast;
    private SharedPreferences mSharedPreferences;

    // 魔镜后台服务器地址
    private String prefUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.setting).setOnClickListener(this);
        findViewById(R.id.saying).setOnClickListener(this);
        findViewById(R.id.help).setOnClickListener(this);

        // 初始化语音输入控件
        SpeechUtility.createUtility(getApplicationContext(),
                SpeechConstant.APPID +"=" + getResources().getString(R.string.app_id));

        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(MainActivity.this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(MainActivity.this, mInitListener);

        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
                Activity.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 随机设置背景图片
        int bgId = (int)(Math.random() * 8) + 1;
        String bgStr = "main_bg" + bgId;
        this.getWindow().setBackgroundDrawableResource(
                getResources().getIdentifier(bgStr, "drawable", getPackageName()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 进入参数设置页面
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, IatSettings.class);
                startActivity(intent);
                break;
            // 开始听写
            // 如何判断一次听写结束：OnResult isLast=true 或者 onError
            case R.id.saying:
                // 移动数据分析，收集开始听写事件
                FlowerCollector.onEvent(MainActivity.this, "iat_recognize");
                // 清理上次听写结果
                mIatResults.clear();
                // 设置参数
                setParam();
                // 显示听写对话框
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();
                showTip(getString(R.string.text_begin));
                break;
            // 帮助
            case R.id.help:
                //showTip("点击话筒图标开始说话");
                //showPopupWindow();
                showHelpDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            // 必须在检查 isLast 前调用 printResult，确保所有听写结果记入 mIatResults
            String result = printResult(results);
            if (isLast) {
                sendMessage(result);
            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }
    };

    /**
     * 显示语音输入结果
     * @param results 语音识别对象
     * @return 语音输入结果
     */
    private String printResult(RecognizerResult results) {
        String result;
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            Log.e(TAG, "语音消息JSON解析异常", e);
        }

        mIatResults.put(sn, text);

        StringBuilder resultBuffer = new StringBuilder();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        result = resultBuffer.toString();
        showTip(result);

        return result;
    }

    /**
     * 参数设置
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        String mEngineType = SpeechConstant.TYPE_CLOUD;
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        // 设置魔镜后台服务器地址
        prefUrl = mSharedPreferences.getString(
                getResources().getString(R.string.pref_key_url), "");

        // 设置语言
        String lag = mSharedPreferences.getString(
                getResources().getString(R.string.pref_key_language), "mandarin");
        if (lag.equals("en_us")) {
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 中文需要设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString(
                getResources().getString(R.string.pref_key_vadbos), "3000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString(
                getResources().getString(R.string.pref_key_vadeos), "2000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString(
                getResources().getString(R.string.pref_key_punc), "1"));
    }

    /**
     * 向魔镜后台发送消息
     * @param msg 待发送的消息
     */
    private void sendMessage(final String msg) {
        final String url = "http://" + prefUrl + "/server/command";

        // 为网络访问操作建立子线程
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String encodedMsg = URLEncoder.encode(msg,"UTF-8");
                    HttpRequest.sendGet(url, "message=" + encodedMsg);
                } catch (IOException e) {
                    Log.d(TAG, "向魔镜发送语音消息出现异常，" +
                            "请检查魔镜服务器地址配置是否正确、魔镜程序是否正确开启！");
                    Log.e(TAG, "魔镜服务器连接异常", e);
                }
            }
        });
        exec.shutdown();
    }

    /**
     * 使用 PopupWindow 弹出帮助提示
     */
    @SuppressWarnings("unused")
    private void showPopupWindow() {
        View popupView = LayoutInflater.from(this)
                .inflate(R.layout.help_window, new RelativeLayout(this), true);
        PopupWindow window = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.showAtLocation(findViewById(R.id.activity_main), Gravity.CENTER, 0, 380);
    }

    /**
     * 显示帮助对话框
     */
    private void showHelpDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("关于");
        builder.setMessage("这是魔镜的语音输入小助手，您可以点击下方的话筒图标开始说话。");
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    /**
     * 显示 Toast
     * @param str 待显示的字符串信息
     */
    private void showTip(String str) {
        // 防止弹出空提示
        if (str == null || str.equals("")) {
            return;
        }

        if(mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 380);
        }
        mToast.setText(str);
        mToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(MainActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(MainActivity.this);
        super.onPause();
    }
}
