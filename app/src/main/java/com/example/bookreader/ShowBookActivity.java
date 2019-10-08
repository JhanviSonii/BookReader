package com.example.bookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowBookActivity extends AppCompatActivity {

    TextView desc,title,url;
    ImageView bookPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);

        Intent intent = getIntent();
        findViews();

        desc.setText(intent.getStringExtra("book_desc"));
        title.setText(intent.getStringExtra("book_title"));
        url.setText(intent.getStringExtra("book_pdf_url"));
        Glide.
                with(getApplicationContext())


                .load(intent.getStringExtra("book_photo")).into(bookPhoto);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("URL",url.getText().toString());
                startActivity(intent);
            }
        });


    }

    public void findViews(){

        desc = findViewById(R.id.book_desc);
        title =findViewById(R.id.book_title);
        url = findViewById(R.id.book_pdf_url);
        bookPhoto =findViewById(R.id.book_photo);

    }
}
