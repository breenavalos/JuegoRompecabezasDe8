package um.tp.juego_de_numeros;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminBase extends SQLiteOpenHelper {

    public AdminBase(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

       public void onCreate(SQLiteDatabase db) {
           db.execSQL("create table usuario (dni integer primary key, nombre text, cantIntentos integer)");
       }

       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("drop table if exists usuario");
           db.execSQL("create table usuario (dni integer primary key, nombre text, cantIntentos integer)");
       }
}
