package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.edu.ifsp.mybooks.R;

public class ListBooksActivity extends AppCompatActivity {

    private Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        addbtn = (Button) findViewById(R.id.bttonadd);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListBooksActivity.this, ReadIsbnCodeActivity.class);
                startActivity(intent);
            }
        });
    }
}
