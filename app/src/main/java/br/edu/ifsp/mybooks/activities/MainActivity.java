package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sembozdemir.booksapi.library.BooksApi;
import com.sembozdemir.booksapi.library.ItemCallback;
import com.sembozdemir.booksapi.library.models.Item;

import br.edu.ifsp.mybooks.R;
import br.edu.ifsp.mybooks.database.DatabaseHandler;
import br.edu.ifsp.mybooks.model.Usuario;
import br.edu.ifsp.mybooks.singleton.UserSingleton;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText passw;
    private ImageView loginButton;
    private TextView registerTextView;

    private UserSingleton userSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.usernameLogin);
        passw = (EditText) findViewById(R.id.passwLogin);
        loginButton = (ImageView) findViewById(R.id.buttonLogin);
        registerTextView = (TextView) findViewById(R.id.register);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler dh = new DatabaseHandler(MainActivity.this);

                Usuario usuario = dh.getUsuario(email.getText().toString());

                if (usuario.getSenha().equals(passw.getText().toString())) {
                    //Fazer login
                    userSingleton = UserSingleton.getUserSingleton();
                    userSingleton.user = usuario;
                    Intent intent = new Intent(MainActivity.this, ListBooksActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Informações inválidas!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //https://github.com/sembozdemir/BooksApiJavaWrapper
        BooksApi booksApi = BooksApi.create("AIzaSyCQXgCdrMuyqHU21GXm3v0vtt-5dw0uWrk");
        booksApi.getBookForIsbn("9788551002391", new ItemCallback() {
            @Override
            public void onSuccess(Item item) {
                Log.v("Livro", item.getVolumeInfo().getTitle());
            }

            @Override
            public void onFailure(Throwable throwable) {
                // handle exception
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userSingleton.user != null) {
            finish();
        }
    }
}
