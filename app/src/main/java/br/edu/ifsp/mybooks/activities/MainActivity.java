package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.edu.ifsp.mybooks.R;

public class MainActivity extends AppCompatActivity {

    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerTextView = (TextView) findViewById(R.id.register);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
