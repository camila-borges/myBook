package br.edu.ifsp.mybooks.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sembozdemir.booksapi.library.BooksApi;

import org.w3c.dom.Text;

import br.edu.ifsp.mybooks.R;
import br.edu.ifsp.mybooks.controller.Adapter;
import br.edu.ifsp.mybooks.database.DatabaseHandler;
import br.edu.ifsp.mybooks.model.Livro;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ViewBookDetailActivity extends AppCompatActivity {

    private ImageView bookImageDetails;
    private TextView bookRateDetails;
    private TextView bookTitleDetails;
    private TextView bookAuthorDetails;
    private TextView bookCommentariesDetails;
    private DatabaseHandler dh;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_detail);

        bookImageDetails = (ImageView) findViewById(R.id.bookImageDetails);
        bookRateDetails = (TextView) findViewById(R.id.bookRateDetails);
        bookTitleDetails = (TextView) findViewById(R.id.bookTitleDetails);
        bookAuthorDetails = (TextView) findViewById(R.id.bookAuthorDetails);
        bookCommentariesDetails = (TextView) findViewById(R.id.bookCommentariesDetails);

        adapter = new Adapter(this);

        dh = new DatabaseHandler(this);

        Intent intent = getIntent();

        int idBook = intent.getIntExtra("livroId", 1);

        Livro selectedLivro = dh.getLivro(idBook);

        bookRateDetails.setText(selectedLivro.getQualidade());
        bookTitleDetails.setText(selectedLivro.getNome());
        bookAuthorDetails.setText(selectedLivro.getAutor());
        bookCommentariesDetails.setText(selectedLivro.getDescricao());

        Glide.with(this)
                .load(selectedLivro.getImagem())
                .transition(withCrossFade())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(bookImageDetails);

    }
}
