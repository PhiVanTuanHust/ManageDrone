package com.manage.drone.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manage.drone.MainActivity;
import com.manage.drone.R;
import com.manage.drone.utils.SharePref;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phí Văn Tuấn on 24/11/2018.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    SharePref pref;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        pref=new SharePref(this);
    }

    @Override
    protected void initData() {
        if (!pref.getBooleanValue(SharePref.IS_LOGIN)){
            onLoginSuccess();
            return;
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {


        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xác thực...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
    }



    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        pref.putBoolean(SharePref.IS_LOGIN,false);
       Intent intent=new Intent(LoginActivity.this,MainActivity.class);
       startActivity(intent);
       finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Nhập địa chỉ email hợp lệ");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() >8) {
            _passwordText.setError("Từ 6 đến 8 ký tự chữ và số");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
