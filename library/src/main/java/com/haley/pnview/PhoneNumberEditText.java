package com.haley.pnview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanglei on 6/24/16.
 */
public class PhoneNumberEditText extends EditText {

    public PhoneNumberEditText(Context context) {
        super(context);
    }

    public PhoneNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneNumberEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init();
    }

    private void init() {
        setRawInputType(InputType.TYPE_CLASS_PHONE);
        String digits = "0123456789";
        setKeyListener(DigitsKeyListener.getInstance(digits));
        removeTextChangedListener(textWatchListener);
        addTextChangedListener(textWatchListener);
    }

    public final TextWatcher textWatchListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
            String str = text.toString().replace(" ", "");
            if (str.length() > 0 && str.length() <= 11) {

                boolean deleteEmpty = false;

                String pattern1 = "^(\\d{7}) (/\\d{4})$";//1835723 7923
                String pattern2 = "^(\\d{3}) (\\d{8})$";//183 57127923

                Pattern r1 = Pattern.compile(pattern1);
                Pattern r2 = Pattern.compile(pattern2);

                Matcher m1 = r1.matcher(text.toString());
                Matcher m2 = r2.matcher(text.toString());
                if (m1.find()) {
                    deleteEmpty = true;
                } else if (m2.find()) {
                    deleteEmpty = true;
                }

                if (deleteEmpty && lengthBefore == 1) {
                    start = start - 1;
                    text = text.toString().substring(0, start) + text.toString().substring(start + 1);
                    str = text.toString().replace(" ", "");
                }

                String endStr = "";
                int len = str.length();
                for (int i = 0; i < len; i++) {
                    endStr += str.charAt(i);
                    if ((i + 2) % 4 == 0 && (i + 1) != len) {
                        endStr += " ";
                    }
                }

                if (endStr.endsWith(" ")) {
                    endStr = endStr.substring(0, endStr.lastIndexOf(" "));
                }

                removeTextChangedListener(this);
                setText(endStr);
                addTextChangedListener(this);

                //计算光标位置
                if (lengthAfter == 0) {//删除
                    int selValue = start - (deleteEmpty ? 1 : 0) - (text.length() - endStr.length());
                    setSelection(selValue);
                } else if (lengthAfter == 1) {//输入一个数字
                    if ((start - 3) % 5 == 0) {
                        start++;
                    }
                    setSelection(start + lengthAfter);
                } else if (lengthAfter > 1) {//输入多个数字
                    setSelection(getText().toString().length());
                }
            } else {
                removeTextChangedListener(this);
                setText(str);
                addTextChangedListener(this);
                setSelection(getText().toString().length());
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
