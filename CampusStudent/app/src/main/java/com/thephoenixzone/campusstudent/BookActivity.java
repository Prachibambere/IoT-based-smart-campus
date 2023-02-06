package com.thephoenixzone.campusstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.thephoenixzone.campusstudent.adapter.AttendanceAdapter;
import com.thephoenixzone.campusstudent.adapter.BookAdapter;
import com.thephoenixzone.campusstudent.model.Attendance;
import com.thephoenixzone.campusstudent.model.Book;
import com.thephoenixzone.campusstudent.util.FixedGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookActivity extends AppCompatActivity {
    ProgressDialog dialog;
    PrefManager prefManager;

    List<Book> bookList = new ArrayList<>();


    int scrollX = 0;
    RecyclerView rvBook;

    HorizontalScrollView headerScroll;

    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        prefManager = new PrefManager(this);

        rvBook = (RecyclerView) findViewById(R.id.rvBooks);
        headerScroll = findViewById(R.id.headerScroll);

        rvBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        bookDetails();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bookDetails() {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerUtility.url_get_book_info(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has(ServerUtility.TAG_SUCCESS)) {
                                JSONArray array = jsonObject.getJSONArray("BookInfo");
                                for (int i = 0; i < array.length(); i++) {
                                    //get subject and attendance details
                                    JSONObject json = array.getJSONObject(i);

                                    String title = json.getString("book_title");
                                    String author = json.getString("book_author");
                                    String datetime = json.getString("date_time");
                                    String returnDate = json.getString("return_date");
                                    String duecharges = json.getString("due_charges");
                                    String status = json.getString("status");

                                    Book attendance = new Book(title, author, datetime, returnDate, duecharges, status);
                                    bookList.add(attendance);
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
                Toast.makeText(BookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(BookActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setUpRecyclerView() {
        bookAdapter = new BookAdapter(BookActivity.this, bookList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvBook.setLayoutManager(manager);
        rvBook.setAdapter(bookAdapter);
        rvBook.addItemDecoration(new DividerItemDecoration(BookActivity.this, DividerItemDecoration.VERTICAL));
    }
}