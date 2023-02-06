package com.thephoenixzone.campusstudent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText txtUsername, txtPassword, etServerIP;

    private PrefManager prefManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        etServerIP = findViewById(R.id.etServerIP);

        btnLogin = (Button) findViewById(R.id.btn_signUp);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);


        prefManager = new PrefManager(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etServerIP.getText().toString().equals("")) {
                    etServerIP.setError("Enter Server IP");
                    etServerIP.requestFocus();
                    return;
                }
                ServerUtility.Server_URL = "http://" + etServerIP.getText().toString() + "/SmartCampus/";
                String email = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    txtUsername.setError("Enter email");
                    txtUsername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    txtPassword.setError("Enter Password");
                    txtPassword.requestFocus();
                    return;
                }

                loginDetails();
            }
        });
    }

    private void loginDetails() {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerUtility.url_get_user_info(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has(ServerUtility.TAG_SUCCESS)) {

                                prefManager.setUserName(jsonObject.getString("username"));
                                prefManager.setUserMobile(jsonObject.getString("mno"));
                                prefManager.setUserEmail(jsonObject.getString("email"));
                                prefManager.setUserAddress(jsonObject.getString("address"));
                                prefManager.setUserType(jsonObject.getString("year"));
                                prefManager.setUserPassword(jsonObject.getString("department"));
                                prefManager.setEmpId(jsonObject.getString("rfid"));

                                SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.setContentText("Login Success...")
                                        .setTitleText("Success")
                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                dialog.dismiss();
//
                                                startActivity();

                                            }
                                        });
                                dialog.show();
                            } else {
                                SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.setContentText("Invalid Credentials...")
                                        .setTitleText(ServerUtility.TAG_ALERT)
                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                dialog.dismiss();
                                            }
                                        });
                                dialog.show();
                            }
                            hud.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("JSONError: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String password = txtPassword.getText().toString();
                String email = txtUsername.getText().toString();
                params.put("email", email);
                params.put("password", password);
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void startActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}