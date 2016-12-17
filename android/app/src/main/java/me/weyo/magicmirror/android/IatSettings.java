package me.weyo.magicmirror.android;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.iflytek.speech.util.SettingTextWatcher;
import com.iflytek.sunflower.FlowerCollector;

import java.util.HashMap;

/**
 * 听写设置界面
 */
public class IatSettings extends PreferenceActivity implements OnPreferenceChangeListener, OnSharedPreferenceChangeListener {
	
	public static final String PREFER_NAME = "com.iflytek.setting";
    private Toolbar mActionBar;
    private HashMap<String, String> prefKeyValue = new HashMap<>();
    private HashMap<String, DialogPreference> keyPreference = new HashMap<>();


    @SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getPreferenceManager().setSharedPreferencesName(PREFER_NAME);
		addPreferencesFromResource(R.xml.iat_setting);
        mActionBar.setTitle(getTitle());

        initPrefMaps();
	}

    /**
     * 初始化参数 Map
     */
    @SuppressWarnings("deprecation")
    private void initPrefMaps() {
        String pref_key_url = getResources().getString(R.string.pref_key_url);
        String pref_default_url = getResources().getString(R.string.pref_default_url);
        String pref_key_vadbos = getResources().getString(R.string.pref_key_vadbos);
        String pref_default_vadbos = getResources().getString(R.string.pref_default_vadbos);
        String pref_key_vadeos = getResources().getString(R.string.pref_key_vadeos);
        String pref_default_vadeos = getResources().getString(R.string.pref_default_vadeos);
        String pref_key_lang = getResources().getString(R.string.pref_key_language);
        String pref_default_lang = getResources().getString(R.string.pref_default_language);
        String pref_key_punc = getResources().getString(R.string.pref_key_punc);
        String pref_default_punc = getResources().getString(R.string.pref_default_punc);

        prefKeyValue.put(pref_key_url, pref_default_url);
        prefKeyValue.put(pref_key_vadbos, pref_default_vadbos);
        prefKeyValue.put(pref_key_vadeos, pref_default_vadeos);
        prefKeyValue.put(pref_key_lang, pref_default_lang);
        prefKeyValue.put(pref_key_punc, pref_default_punc);

        EditTextPreference mUrlPreference = (EditTextPreference) findPreference(pref_key_url);

        EditTextPreference mVadbosPreference = (EditTextPreference) findPreference(pref_key_vadbos);
        mVadbosPreference.getEditText().addTextChangedListener(
                new SettingTextWatcher(IatSettings.this, mVadbosPreference,0,10000));

        EditTextPreference mVadeosPreference = (EditTextPreference) findPreference(pref_key_vadeos);
        mVadeosPreference.getEditText().addTextChangedListener(
                new SettingTextWatcher(IatSettings.this, mVadeosPreference,0,10000));

        ListPreference mLangPreference = (ListPreference) findPreference(pref_key_lang);

        ListPreference mPuncPreference = (ListPreference) findPreference(pref_key_punc);

        keyPreference.put(pref_key_url, mUrlPreference);
        keyPreference.put(pref_key_vadbos, mVadbosPreference);
        keyPreference.put(pref_key_vadeos, mVadeosPreference);
        keyPreference.put(pref_key_lang, mLangPreference);
        keyPreference.put(pref_key_punc, mPuncPreference);
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup contentView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.settings_toolbar, new LinearLayout(this), false);

        mActionBar = (Toolbar) contentView.findViewById(R.id.action_bar);
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewGroup contentWrapper = (ViewGroup) contentView.findViewById(R.id.content_wrapper);
        LayoutInflater.from(this).inflate(layoutResID, contentWrapper, true);

        getWindow().setContentView(contentView);
    }

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		return true;
	}

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(this);

        initSummary();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPause() {
        super.onPause();
        // 开放统计 移动数据统计分析
        FlowerCollector.onPause(this);

        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        registSummary(key);
    }

    /**
     * 初始化 Summary
     */
    private void initSummary() {
        for (String key : prefKeyValue.keySet()) {
            registSummary(key);
        }
    }

    /**
     * 注册指定参数的 Summary
     * @param key 参数名称
     */
    private void registSummary(String key) {
        DialogPreference preference = keyPreference.get(key);

        String value = null;
        if(preference instanceof EditTextPreference) {
            value = ((EditTextPreference) preference).getText();
        } else {
            CharSequence entry = ((ListPreference) preference).getEntry();
            if(entry != null) {
                value = entry.toString();
            }
        }
        if (value == null || value.equals("")) {
            preference.setSummary(prefKeyValue.get(key));
        } else {
            preference.setSummary(value);
        }
    }
}
