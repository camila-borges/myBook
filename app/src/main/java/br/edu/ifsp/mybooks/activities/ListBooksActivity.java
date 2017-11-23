package br.edu.ifsp.mybooks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.edu.ifsp.mybooks.BooksSample;
import br.edu.ifsp.mybooks.R;

public class ListBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        BooksSample.test("9780785121794");
        Log.v("Book", "entrou activity");
    }
}
