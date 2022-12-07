package um.tp.juego_de_numeros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OperarConBase {
    public static boolean Alta(Context c, String dni, String nombre, String cantIntentos){
        boolean devolver = false;
        AdminBase admin=new AdminBase(c, "baseDeducirNro", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("dni", dni);
        registro.put("nombre", nombre);
        registro.put("cantIntentos", cantIntentos);
        try {
            db.insert("usuario", null, registro);
            db.close();
            devolver = true;
        }
            catch(Exception ex){

        }
        return devolver;
    }
}
