package com.gy.wyy.chat.ui.input;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.face.Emoji;
import com.gy.wyy.chat.ui.face.EmojiFragment;
import com.gy.wyy.chat.ui.face.FaceFragment;
import com.gy.wyy.chat.ui.face.FaceManager;
import com.gy.wyy.chat.ui.more.InputMoreFragment;

/**
 *
 */
public class InputLayout extends LinearLayout implements View.OnClickListener, TextWatcher, InputLayoutInterface {

    private Activity mActivity;

    private ImageView mAudioImageView;
    private Button mAudioButton;
    private boolean mAudioDisable;

    private ImageView mFaceImageView;
    private boolean mFaceDisable;

    private ImageView mMoreImageView;
    private boolean mMoreDisable;

    private EditText mInputEditText;
    private Button sendButton;

    /**
     * 此处只提供展示，具体点击事件外部传入
     */
    private View mMoreLayout;
    private FragmentManager mFragmentManager;

    private boolean inputMoreShow;
    private InputMoreFragment inputMoreFragment;

    private boolean inputFaceShow;
    private FaceFragment faceFragment;

    private View emojiLayout;
    private EmojiFragment emojiFragment;


    public InputLayout(Context context) {
        super(context);
        initView();
    }

    public InputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *
     */
    private void initView(){
        mActivity = (Activity) getContext();
        LayoutInflater.from(getContext()).inflate(R.layout.message_input_layout,this);

        mAudioImageView = findViewById(R.id.message_input_audio);
        mAudioButton = findViewById(R.id.message_input_audio_input);
        mInputEditText = findViewById(R.id.message_input_et);
        mMoreImageView = findViewById(R.id.message_input_more);
        mFaceImageView = findViewById(R.id.message_input_face);
        sendButton = findViewById(R.id.message_input_send);
        mMoreLayout = findViewById(R.id.message_input_group);
        emojiLayout = findViewById(R.id.message_input_emoji);

        mInputEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showInput();
                return false;
            }
        });

        mAudioImageView.setOnClickListener(this);
        mAudioButton.setOnClickListener(this);
        mMoreImageView.setOnClickListener(this);
        mFaceImageView.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        mInputEditText.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.message_input_audio) {
            if (mAudioButton.getVisibility() == View.GONE){
                mAudioImageView.setImageResource(R.drawable.message_input_keyboard);
                hideInput();
                mInputEditText.setVisibility(View.GONE);
                mAudioButton.setVisibility(View.VISIBLE);
            }else {
                mAudioImageView.setImageResource(R.drawable.message_input_audio);
                showInput();
                mAudioButton.setVisibility(View.GONE);
                mInputEditText.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.message_input_audio_input) {

        } else if (id == R.id.message_input_more) {
            if (inputMoreShow){
                inputMoreShow = false;
                mMoreLayout.setVisibility(View.GONE);
            }else {
                showInputMoreFragment();
            }
        } else if (id == R.id.message_input_face) {
            if (inputFaceShow){
                inputFaceShow = false;
                mMoreLayout.setVisibility(View.GONE);
            }else {
                showInputFaceFragment();
            }
        } else if (id == R.id.message_input_send) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s.toString().trim())) {
            sendButton.setVisibility(View.VISIBLE);
            showEmojiFragment(s.toString().trim());
        } else {
            sendButton.setVisibility(View.GONE);
            hindEmojiFragment();
        }
    }

    @Override
    public void disableAudioInput(boolean disable) {
        mAudioDisable = disable;
        if (disable)
            mAudioImageView.setVisibility(View.GONE);
        else
            mAudioImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableFaceInput(boolean disable) {
        mFaceDisable = disable;
        if (disable)
            mFaceImageView.setVisibility(View.GONE);
        else
            mFaceImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableMoreInput(boolean disable) {
        mMoreDisable = disable;
        if (disable)
            mMoreImageView.setVisibility(View.GONE);
        else
            mMoreImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public EditText getInputText() {
        return mInputEditText;
    }

    /* 内部API */

    /**
     *
     */
    private void showInput() {
        mMoreLayout.setVisibility(View.GONE);
        mAudioImageView.setImageResource(R.drawable.message_input_audio);
        mFaceImageView.setImageResource(R.drawable.message_input_face);
        mInputEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mInputEditText, 0);
    }

    /**
     *
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInputEditText.getWindowToken(), 0);
        mInputEditText.clearFocus();
        mMoreLayout.setVisibility(View.GONE);
    }

    /**
     *
     */
    private void showInputMoreFragment() {
        if (mFragmentManager == null) {
            mFragmentManager = mActivity.getFragmentManager();
        }
        if (inputMoreFragment == null)
            inputMoreFragment = new InputMoreFragment();
        inputMoreShow = true;
        hideInput();
        mMoreLayout.setVisibility(View.VISIBLE);
        mFragmentManager.beginTransaction().replace(R.id.message_input_group, inputMoreFragment).commitAllowingStateLoss();
    }

    /**
     *
     * @param key
     */
    private void showEmojiFragment(String key){
        if (mFragmentManager == null) {
            mFragmentManager = mActivity.getFragmentManager();
        }
        if (emojiFragment == null)
            emojiFragment = new EmojiFragment();
        emojiLayout.setVisibility(View.VISIBLE);
        mFragmentManager.beginTransaction().replace(R.id.message_input_emoji, emojiFragment).commitAllowingStateLoss();
        emojiFragment.emojiMatching(key);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                hindEmojiFragment();
            }
        },5000);
    }

    /**
     *
     */
    private void hindEmojiFragment(){
        emojiLayout.setVisibility(GONE);
        mInputEditText.setText("");
        if (emojiFragment != null)
            emojiFragment.clear();
    }

    /**
     *
     */
    private void showInputFaceFragment() {
        if (mFragmentManager == null) {
            mFragmentManager = mActivity.getFragmentManager();
        }
        if (faceFragment == null){
            faceFragment = new FaceFragment();
            faceFragment.setListener(new FaceFragment.OnEmojiClickListener() {
                @Override
                public void onEmojiDelete() {
                    int index = mInputEditText.getSelectionStart();
                    Editable editable = mInputEditText.getText();
                    boolean isFace = false;
                    if (index <= 0) {
                        return;
                    }
                    if (editable.charAt(index - 1) == ']') {
                        for (int i = index - 2; i >= 0; i--) {
                            if (editable.charAt(i) == '[') {
                                String faceChar = editable.subSequence(i, index).toString();
                                if (FaceManager.isFaceChar(faceChar)) {
                                    editable.delete(i, index);
                                    isFace = true;
                                }
                                break;
                            }
                        }
                    }
                    if (!isFace) {
                        editable.delete(index - 1, index);
                    }
                }

                @Override
                public void onEmojiClick(Emoji emoji) {
                    int index = mInputEditText.getSelectionStart();
                    Editable editable = mInputEditText.getText();
                    editable.insert(index, emoji.getFilter());
                    FaceManager.handlerEmojiText(mInputEditText, editable.toString(), true);
                }
            });
        }
        inputFaceShow = true;
        hideInput();
        mMoreLayout.setVisibility(View.VISIBLE);
        mFragmentManager.beginTransaction().replace(R.id.message_input_group, faceFragment).commitAllowingStateLoss();
    }

    /* 权限 */

    /**
     * 检查读写权限
     * @return
     */
    protected boolean checkRedAndWritePermission() {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 拍照及录音
     * @return
     */
    protected boolean checkCameraAndAudioPermission() {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) ||
                    PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
                flag = false;
            }
        }
        return flag;
    }

    protected boolean checkCameraPermission() {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     *
     * @return
     */
    protected boolean checkAudioPermission() {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
                flag = false;
            }
        }
        return flag;
    }
}
