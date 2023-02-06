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
import com.thephoenixzone.campusstudent.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Book> attendanceList;
    private List<Book> filteredBookList;
    private Context context;


    public BookAdapter(Context context, List<Book> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
        this.filteredBookList = attendanceList;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtAuthor, txtDateTime, txtReturnDate, txtDueCharges, txtStatus;
        public ImageView imgLogo;

        public BookViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtName);
            txtAuthor = view.findViewById(R.id.txtAuthor);
            txtDateTime = view.findViewById(R.id.txtDateTime);
            txtReturnDate = view.findViewById(R.id.txtReturnDate);
            txtDueCharges = view.findViewById(R.id.txtCharges);
            txtStatus = view.findViewById(R.id.txtStatus);


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
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_book, viewGroup, false);
            return new BookViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_book_colorful,
                    viewGroup, false);
            return new BookViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = filteredBookList.get(position);
        holder.txtTitle.setText(book.getTitle());
        holder.txtAuthor.setText("" + book.getAuthor());
        holder.txtDateTime.setText(book.getDatetime());
        holder.txtReturnDate.setText(book.getReturnDate());
        holder.txtDueCharges.setText(book.getDuecharges());
        holder.txtStatus.setText(book.getStatus());

    }

    @Override
    public int getItemCount() {
        return filteredBookList.size();
    }

}
