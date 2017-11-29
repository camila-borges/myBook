package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.edu.ifsp.mybooks.R;
import br.edu.ifsp.mybooks.database.DatabaseHandler;
import br.edu.ifsp.mybooks.model.Livro;
import br.edu.ifsp.mybooks.singleton.UserSingleton;

public class CreateBookActivity extends AppCompatActivity {

    UserSingleton userSingleton;
    private Integer usuarioLogado;
    private EditText titleBook;
    private EditText authorBook;
    private EditText descrpBook;
    private Button confirmRegister;
    private Button cancelRegister;
    private RadioGroup radioGroupBook;
    private DatabaseHandler dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        titleBook = (EditText) findViewById(R.id.BookNameCreateBook);
        authorBook = (EditText) findViewById(R.id.BookAuthorCreateBook);
        descrpBook = (EditText) findViewById(R.id.TextAreaDescriptionCreateBook);
        confirmRegister = (Button) findViewById(R.id.ButtonConfirmRegister);
        cancelRegister = (Button) findViewById(R.id.ButtonCancelRegister);
        radioGroupBook = (RadioGroup) findViewById(R.id.RadioGroupCreateBook);

        userSingleton = UserSingleton.getUserSingleton();

        usuarioLogado = userSingleton.user.getId();

        dh = new DatabaseHandler(this);

        Intent intent = getIntent();

        if(intent.getBooleanExtra("fromIsbn", false)){
            titleBook.setText(intent.getStringExtra("bookTitle"));
            authorBook.setText(intent.getStringExtra("bookAuthor"));
            descrpBook.setText(intent.getStringExtra("bookDescription"));
        }

        final String imagem = intent.getStringExtra("imagem");
        Toast.makeText(this, imagem, Toast.LENGTH_SHORT).show();

        confirmRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedRadio = radioGroupBook.getCheckedRadioButtonId();

                Livro livro = new Livro();
                String titulo = titleBook.getText().toString();
                String autor = authorBook.getText().toString();
                String descricao = descrpBook.getText().toString();

                switch (selectedRadio){
                    case R.id.RadioButtonBadCreateBook:
                        livro.setQualidade("ruim");
                        break;
                    case R.id.RadioButtonMediumCreateBook:
                        livro.setQualidade("medio");
                        break;
                    case R.id.RadioButtonGoodCreateBook:
                        livro.setQualidade("bom");
                        break;
                    case R.id.RadioButtonVeryGoodCreateBook:
                        livro.setQualidade("otimo");
                        break;
                }

                livro.setNome(titulo);
                livro.setId_user(usuarioLogado);
                livro.setAutor(autor);
                livro.setDescricao(descricao);
                livro.setImagem(imagem);

                dh.addLivro(livro);

                Intent intent = new Intent(CreateBookActivity.this, ListBooksActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
