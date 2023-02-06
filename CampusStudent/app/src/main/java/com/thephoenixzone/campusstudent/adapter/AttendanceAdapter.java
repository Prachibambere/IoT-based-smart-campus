package com.thephoenixzone.campusstudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thephoenixzone.campusstudent.R;
import com.thephoenixzone.campusstudent.model.Attendance;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>{

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Attendance> attendanceList;
    private List<Attendance> filteredBookList;
    private Context context;


    public AttendanceAdapter(Context context, List<Attendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
        this.filteredBookList = attendanceList;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtLectures, txtPresents, txtPercentage;
        public ImageView imgLogo;

        public AttendanceViewHolder(View view) {
            super(view);
            txtPercentage = view.findViewById(R.id.txtPercentage);
            txtName = view.findViewById(R.id.txtName);
            txtPresents = view.findViewById(R.id.txtPresents);
            txtLectures = view.findViewById(R.id.txtLectures);


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }

        return TYPE_ROW;
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_attendance, viewGroup, false);
            return new AttendanceViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_attendance_colorful,
                    viewGroup, false);
            return new AttendanceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        Attendance book = filteredBookList.get(position);
        holder.txtName.setText(book.getSubject());
        holder.txtPresents.setText("" + book.getPresents());
        holder.txtPercentage.setText("" + book.getPercentage());
        holder.txtLectures.setText("" + book.getLectures());

    }

    @Override
    public int getItemCount() {
        return filteredBookList.size();
    }

}
