package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.edu.ifsp.mybooks.R;
import br.edu.ifsp.mybooks.database.DatabaseHandler;
import br.edu.ifsp.mybooks.model.Usuario;

public class CreateUserActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonCancel;
    private Button buttonDone;
    private DatabaseHandler dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        editTextEmail = (EditText) findViewById(R.id.UserEmailCreateUser);
        editTextPassword = (EditText) findViewById(R.id.UserPasswordCreateUser);
        buttonCancel = (Button) findViewById(R.id.ButtonCancelRegister);
        buttonDone = (Button) findViewById(R.id.ButtonDoneRegister);

        dh = new DatabaseHandler(this);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario user = new Usuario();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                user.setEmail(email);
                user.setSenha(password);

                dh.addUsuario(user);

                Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
