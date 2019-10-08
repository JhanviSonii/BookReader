package com.example.bookreader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {
    private List<Book> bookList, filteredBookList;
    Context context;
    DatabaseReference db;

    public BooksAdapter(List<Book> list, Context context) {
        this.bookList = list;
        this.context = context;
        this.filteredBookList = list;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, pdf_url;
        public ImageView bookImage, popupDots;
        private final Context context;
        Book book;



        public MyViewHolder(View view) {

            super(view);
            context = view.getContext();


            title = view.findViewById(R.id.novel_title);
            author = view.findViewById(R.id.author_name);
            bookImage = view.findViewById(R.id.novel_image);
            pdf_url=view.findViewById(R.id.pdf_url);


            popupDots = view.findViewById(R.id.pop_up_dots);
            pdf_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    context.startActivity(Intent.createChooser(emailIntent, "Send Email"));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked

                    Log.i("click","clicked");


                    Intent intent = new Intent(context,ShowBookActivity.class);

                    intent.putExtra("book_photo",book.getPhotoLocation());


                    intent.putExtra("book_title",book.getTitle());
                    intent.putExtra("book_desc",book.getDescription());
                    intent.putExtra("book_pdf_url",book.getUrl());

                    context.startActivity(intent);


                }
            });



        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_book, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Book book = filteredBookList.get(position);
        holder.book=book;
        holder.title.setText("Title: "+book.getTitle());
        holder.author.setText("Author:" + book.getAuthor());
        holder.popupDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users/Public/6dsZEQ2k2SUqjcF8PL4yh62gtXz2").child("favorites").push();
                db.setValue(book.getTitle());

                Toast.makeText(context,"Added to favorites",Toast.LENGTH_SHORT).show();


            }
        });


        /*db = FirebaseDatabase.getInstance().getReference().child("users/Students").child(book.getUploadedBy());

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                holder.seller.setText("Seller: " + dataSnapshot.child("name").getValue(String.class));
                holder.email.setText(dataSnapshot.child("email_id").getValue(String.class));


                Log.i("Holder", book.getUploadedBy());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        final int SIZE = 20 * 1024 * 1024;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("photos/" + book.getPhotoLocation());

        /*storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Log.i("photo",uri.toString());
                Glide.with(context).load(uri.toString()).into(holder.bookImage);
            }
        });*/

        Glide.
                with(context)


                .load(book.getPhotoLocation()).into(holder.bookImage);


        Log.d("imageUrl", "photos/" + book.getPhotoLocation());


    }

    public void showPopUpMenu(View view, final Book book, final Context c) {

        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.card_popup_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.add_to_fav:
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users/Public/6dsZEQ2k2SUqjcF8PL4yh62gtXz2").child("favorites").push();
                        db.setValue(book.getTitle());

                        return true;







                }
                return false;
            }

        });
    }

    /*private void testNoti(final String str, Book book) {


        db = FirebaseDatabase.getInstance().getReference().child("users/Students").child(book.getUploadedBy()).child("Fcm_Token");


        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    Log.i("test", token);

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String receiver = dataSnapshot.getValue(String.class);

                            ServerTest test = new ServerTest();

                            try {


                                new ServerTest().execute(receiver, "Buyer Alert", FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + str);
                            } catch (Exception e) {

                                Log.i("exception", e.toString());

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });


                }

            }
        });
    }*/

    public Filter getFilter() {

        Log.i("test1", "in filter");
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.i("test1", constraint.toString());
                if (constraint.toString() == "") {
                    filteredBookList = bookList;
                }


                List<Book> filteredList = new ArrayList<>();
                for (Book book : bookList) {
                    // Log.i("test1",book.getTitle());
                    if (book.getTitle().toLowerCase().contains(constraint)) {

                        filteredList.add(book);
                        Log.i("test1", book.getTitle());


                    }

                }
                filteredBookList = filteredList;

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredBookList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredBookList = (ArrayList<Book>) results.values;
                notifyDataSetChanged();


            }

        };
    }

    @Override
    public int getItemCount() {


        Log.e("error", "item-count:" + filteredBookList.size());
        return filteredBookList.size();
    }

}
