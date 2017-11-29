package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sembozdemir.booksapi.library.BooksApi;
import com.sembozdemir.booksapi.library.ItemCallback;
import com.sembozdemir.booksapi.library.models.Item;

import java.util.List;

import br.edu.ifsp.mybooks.R;

public class ReadIsbnCodeActivity extends AppCompatActivity {

    private BooksApi booksApi;
    private EditText isbnEditText;
    private Button confirmButton;
    private Button manuallyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_isbn_code);

        isbnEditText = (EditText) findViewById(R.id.WriteBookIsbn);
        manuallyButton = (Button) findViewById(R.id.ButtonRegisterManuallyIsbn);
        confirmButton = (Button) findViewById(R.id.ButtonDoneIsbn);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isbnToSearch = isbnEditText.getText().toString();

                booksApi = BooksApi.create("AIzaSyCQXgCdrMuyqHU21GXm3v0vtt-5dw0uWrk");
                booksApi.getBookForIsbn(isbnToSearch, new ItemCallback() {
                    @Override
                    public void onSuccess(Item item) {
                        String bookTitle = item.getVolumeInfo().getTitle();
                        String bookDescription = item.getSearchInfo().getTextSnippet();
                        List<String> bookAuthor = item.getVolumeInfo().getAuthors();

                        Intent intent = new Intent(ReadIsbnCodeActivity.this, CreateBookActivity.class);
                        intent.putExtra("bookTitle", bookTitle);
                        intent.putExtra("bookDescription", bookDescription);
                        intent.putExtra("fromIsbn", true);
                        intent.putExtra("bookAuthor", bookAuthor.get(0));

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(ReadIsbnCodeActivity.this, "Livro não encontrado!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        manuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentManual = new Intent(ReadIsbnCodeActivity.this, CreateBookActivity.class);
                intentManual.putExtra("fromIsbn", false);

                startActivity(intentManual);
            }
        });

    }

}
