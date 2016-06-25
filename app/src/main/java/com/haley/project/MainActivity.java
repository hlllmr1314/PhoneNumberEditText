package com.haley.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
//        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        String str = text.toString().replace(" ", "");
        if (str.length() > 0) {
            String endStr = "";
            int len = str.length();
            for (int i = 0; i < len; i++) {
                endStr += str.charAt(i);
                if ((i + 2) % 4 == 0 && (i + 1) != len) {
                    endStr += " ";
                }
            }
            if (lengthAfter != 0) {
                editText.removeTextChangedListener(this);
                editText.setText(endStr);
                editText.addTextChangedListener(this);
            }
        }

        if (lengthAfter == 0) {
            editText.setSelection(start + lengthAfter);
        } else if (lengthAfter == 1) {
            if ((start - 3) % 5 == 0) {
                start++;
            }
            editText.setSelection(start + lengthAfter);
        } else if (lengthAfter > 1) {
            editText.setSelection(editText.getText().toString().length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
