package br.edu.ifsp.mybooks.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.ifsp.mybooks.model.Usuario;

/**
 * Created by Camila on 12/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "myBooks";

    private static final String TABLE_USUARIOS = "users";

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE" + TABLE_USUARIOS + "("
            + KEY_ID + "INTEGER PRIMARY KEY, " + KEY_EMAIL + "TEXT,"
            + KEY_SENHA + "TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USUARIOS);

        onCreate(db);
    }

    void addUser(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, usuario.getEmail());
        values.put(KEY_SENHA, usuario.getSenha());

        db.insert(TABLE_USUARIOS, null, values);
        db.close();
    }

    Usuario getUsuario(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USUARIOS, new String[] { KEY_ID,
            KEY_EMAIL, KEY_SENHA}, KEY_ID + "=?",
            new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Usuario usuario = new Usuario(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return usuario;
    }

    public int updateUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, usuario.getEmail());
        values.put(KEY_SENHA, usuario.getSenha());

        return db.update(TABLE_USUARIOS, values, KEY_ID + "=?",
                new String[]{ String.valueOf(usuario.getId()) });
    }

    public void deleteUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USUARIOS, KEY_ID + " = ? ",
                new String[]{ String.valueOf(usuario.getId()) });
        db.close();
    }
}
