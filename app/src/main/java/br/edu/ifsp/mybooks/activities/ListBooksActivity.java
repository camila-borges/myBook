package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.mybooks.R;
import br.edu.ifsp.mybooks.controller.Adapter;
import br.edu.ifsp.mybooks.database.DatabaseHandler;
import br.edu.ifsp.mybooks.model.Livro;

public class ListBooksActivity extends AppCompatActivity {

    private Button addbtn;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private EditText textBook;



    private List<Livro> arrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater minfl = getMenuInflater();
        minfl.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuItem1:
                String textMessageBox = "Essa aplicação foi desenvolvida por Camila, tem como objetivo a criação de uma " +
                        "biblioteca virtual própria. Para trabalhos futuros o objetivo será transformar esta aplicação" +
                        " em uma rede social para compartilhamento de informações, sobre livros entre os usuários.";
                messageBox("Sobre", textMessageBox);
                break;
            case R.id.menuItem2:
                Intent intent = new Intent(ListBooksActivity.this, ReadIsbnCodeActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
//        addbtn = (Button) findViewById(R.id.bttonadd);
        textBook = (EditText) findViewById(R.id.searchBookList);
        DatabaseHandler db = new DatabaseHandler(this);
        arrayList = db.getAllLivros();
        prepareRecyclerView();


//        addbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ListBooksActivity.this, ReadIsbnCodeActivity.class);
//                startActivity(intent);
//            }
//        });

        textBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String query = charSequence.toString();
                query = query.toLowerCase();
                ArrayList<Livro> newList = new ArrayList<>();
                for (Livro entry : arrayList) {
                    if (entry.getNome().toLowerCase().contains(query)) {
                        newList.add(entry);
                    }
                }
                adapter.setFilter(newList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler db = new DatabaseHandler(this);
        arrayList = db.getAllLivros();
    }


    private void messageBox(String title, String message) {

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle(title);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }

    private void prepareRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setLivroList(arrayList);
    }
}
