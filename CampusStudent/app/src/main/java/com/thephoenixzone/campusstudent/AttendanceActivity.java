package com.thephoenixzone.campusstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

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
import com.thephoenixzone.campusstudent.adapter.AttendanceAdapter;
import com.thephoenixzone.campusstudent.model.Attendance;
import com.thephoenixzone.campusstudent.util.FixedGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {
    ProgressDialog dialog;
    PrefManager prefManager;

    List<Attendance> attendanceList = new ArrayList<>();


    int scrollX = 0;
    RecyclerView rvAttendance;

    HorizontalScrollView headerScroll;

    AttendanceAdapter attendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        prefManager = new PrefManager(this);

        rvAttendance = (RecyclerView) findViewById(R.id.rvAttendance);
        headerScroll = findViewById(R.id.headerScroll);

        rvAttendance.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        attendanceDetails();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void attendanceDetails() {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerUtility.url_get_attendance_info(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has(ServerUtility.TAG_SUCCESS)) {
                                JSONArray array = jsonObject.getJSONArray("AttendanceInfo");
                                for (int i = 0; i < array.length(); i++) {
                                    //get subject and attendance details
                                    JSONObject json = array.getJSONObject(i);

                                    String subject = json.getString("subject");
                                    int lectures = json.getInt("lectures");
                                    int presents = json.getInt("presents");
                                    double percentage = json.getDouble("percentage");

                                    Attendance attendance = new Attendance(subject, lectures, presents, percentage);
                                    attendanceList.add(attendance);
                                }
                                setUpRecyclerView();
                            }
                            hud.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttendanceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("JSONError: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String rfid = prefManager.getEmpId();
                params.put("rfid", rfid);
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
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setUpRecyclerView() {
        attendanceAdapter = new AttendanceAdapter(AttendanceActivity.this, attendanceList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvAttendance.setLayoutManager(manager);
        rvAttendance.setAdapter(attendanceAdapter);
        rvAttendance.addItemDecoration(new DividerItemDecoration(AttendanceActivity.this, DividerItemDecoration.VERTICAL));
    }
}