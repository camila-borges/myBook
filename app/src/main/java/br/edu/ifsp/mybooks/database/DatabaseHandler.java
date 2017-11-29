package br.edu.ifsp.mybooks.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.mybooks.model.Livro;
import br.edu.ifsp.mybooks.model.Usuario;

/**
 * Created by Camila on 12/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "myBooks";

    private static final String TABLE_USUARIOS = "users";

    private static final String TABLE_BOOKS = "books";

    private static final String KEY_BOOK_ID = "id";
    private static final String KEY_BOOK_USER = "userid";
    private static final String KEY_BOOK_DESCRIPTION = "description";
    private static final String KEY_BOOK_NAME = "name";
    private static final String KEY_BOOK_AUTHOR = "author";
    private static final String KEY_BOOK_QUALITY = "quality";

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USUARIOS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EMAIL + " TEXT unique not null, "
            + KEY_SENHA + " TEXT not null" + ")";

        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + KEY_BOOK_ID + " INTEGER PRIMARY KEY, " + KEY_BOOK_NAME + " TEXT unique not null, "
                + KEY_BOOK_DESCRIPTION + " TEXT not null, " + KEY_BOOK_AUTHOR + " TEXT not null, "
                + KEY_BOOK_QUALITY + " TEXT not null, " + KEY_BOOK_USER + " INTEGER, "
                + " FOREIGN KEY ("+KEY_BOOK_USER+") REFERENCES "+TABLE_USUARIOS+" ("+KEY_ID+"));";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);

        onCreate(db);
    }

    public void addUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, usuario.getEmail());
        values.put(KEY_SENHA, usuario.getSenha());

        db.insert(TABLE_USUARIOS, null, values);
        db.close();
    }

    public void addLivro(Livro livro){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_NAME, livro.getNome());
        values.put(KEY_BOOK_DESCRIPTION, livro.getDescricao());
        values.put(KEY_BOOK_AUTHOR, livro.getAutor());
        values.put(KEY_BOOK_QUALITY, livro.getQualidade());
        values.put(KEY_BOOK_USER, livro.getId_user());

        db.insert(TABLE_BOOKS, null, values);
        db.close();
    }

    public Usuario getUsuario(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USUARIOS, new String[] { KEY_ID,
                        KEY_EMAIL, KEY_SENHA}, KEY_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Usuario usuario = new Usuario(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return usuario;
    }

    public Livro getLivro(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKS, new String[] { KEY_BOOK_ID,
                        KEY_BOOK_NAME, KEY_BOOK_DESCRIPTION, KEY_BOOK_AUTHOR, KEY_BOOK_QUALITY, KEY_BOOK_USER}, KEY_BOOK_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Livro livro = new Livro(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return livro;
    }

    public Usuario getUsuario(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USUARIOS, new String[] { KEY_ID,
                        KEY_EMAIL, KEY_SENHA}, KEY_EMAIL + " = ?",
                new String[] {String.valueOf(email)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Usuario usuario = new Usuario(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return usuario;
    }

    public Livro getLivro(String nome){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKS, new String[] { KEY_BOOK_ID,
                        KEY_BOOK_NAME, KEY_BOOK_DESCRIPTION, KEY_BOOK_AUTHOR, KEY_BOOK_QUALITY, KEY_BOOK_USER}, KEY_BOOK_NAME
                        + " = ?",
                new String[] {String.valueOf(nome)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Livro livro = new Livro(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return livro;
    }

    public List<Livro> getAllLivros() {
        List<Livro> livroList = new ArrayList<Livro>();

        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Livro livro = new Livro();
                livro.setId(Integer.parseInt(cursor.getString(0)));
                livro.setNome(cursor.getString(1));
                livro.setDescricao(cursor.getString(2));
                livro.setAutor(cursor.getString(3));
                livro.setQualidade(cursor.getString(4));
                livro.setId_user(Integer.parseInt(cursor.getString(5)));

                // Adding to list
                livroList.add(livro);
            } while (cursor.moveToNext());
        }

        // return contact list
        return livroList;
    }

    public int updateUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, usuario.getEmail());
        values.put(KEY_SENHA, usuario.getSenha());

        return db.update(TABLE_USUARIOS, values, KEY_ID + " = ?",
                new String[]{ String.valueOf(usuario.getId()) });
    }

    public void deleteUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USUARIOS, KEY_ID + " = ?",
                new String[]{ String.valueOf(usuario.getId()) });
        db.close();
    }

    public void deleteLivro(Livro livro){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, KEY_ID + " = ?",
                new String[]{ String.valueOf(livro.getId()) });
        db.close();
    }
}
