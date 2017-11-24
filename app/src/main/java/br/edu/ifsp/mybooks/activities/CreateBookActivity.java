package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import br.edu.ifsp.mybooks.R;

public class CreateBookActivity extends AppCompatActivity {

    private EditText titleBook;
    private EditText authorBook;
    private EditText descrpBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        titleBook = (EditText) findViewById(R.id.BookNameCreateBook);
        authorBook = (EditText) findViewById(R.id.BookAuthorCreateBook);
        descrpBook = (EditText) findViewById(R.id.TextAreaDescriptionCreateBook);

        Intent intent = getIntent();

        if(intent.getBooleanExtra("fromIsbn", false)){
            titleBook.setText(intent.getStringExtra("bookTitle"));
            authorBook.setText(intent.getStringExtra("bookAuthor"));
            descrpBook.setText(intent.getStringExtra("bookDescription"));
        }
    }
}
