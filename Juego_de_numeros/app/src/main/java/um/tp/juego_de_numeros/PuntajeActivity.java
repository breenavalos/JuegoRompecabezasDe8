package um.tp.juego_de_numeros;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PuntajeActivity extends AppCompatActivity {

    private Button btnRegistrar, btnSalir, btnResetearTabla;
    private TextView textViewDni, textViewNombre, textViewCantIntentos, textViewIntentos;
    private static int cantIntentos;
    private ScrollView listaIntentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje);

        textViewDni = findViewById(R.id.textViewDni);
        textViewDni.requestFocus();
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewCantIntentos = findViewById(R.id.textViewCantIntentos);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener((v) -> { validarDatos(v);});

        btnSalir = findViewById(R.id.btnSalir2);
        btnSalir.setOnClickListener((v) -> { finish();});

        btnResetearTabla = findViewById(R.id.btnResetearTabla);
        btnResetearTabla.setOnClickListener((v) -> { resetearTabla("usuario");});


        cantIntentos = GameActivity.cantIntentos;

        textViewIntentos = findViewById(R.id.textViewIntentos);
        textViewIntentos.setText(""+cantIntentos+"");

    }

    public void validarDatos(View v){
        String a = textViewDni.getText().toString();
        String b = textViewNombre.getText().toString();
        if (a.isEmpty())
            textViewDni.setError("Debe ingresar un nro de dni!");
        else
            if (b.isEmpty())
                textViewNombre.setError("Debe ingresar un nombre!");
            else
                consulta(v);
    }

    public void alta(View v){
        String dni = textViewDni.getText().toString();
        String nombre = textViewNombre.getText().toString();
        String cantIntentos = textViewIntentos.getText().toString();

        boolean devolver = OperarConBase.Alta(this, dni, nombre, cantIntentos);
        if(devolver){
            //textViewDni.setText("");
            //textViewNombre.setText("");
            //textViewIntentos.setText("");
            Toast.makeText(this, "Datos del jugador cargados", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "ERROR no se cargaron los datos", Toast.LENGTH_SHORT).show();
    }

    public Cursor consulta(View v){
        AdminBase admin = new AdminBase(this, "baseDeducirNro", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = textViewDni.getText().toString();
        Cursor fila = null;
        try{
            fila = bd.rawQuery("select nombre, cantIntentos from usuario where dni=" + dni, null);
            if(fila.moveToFirst() ){
                modificacion(v);
            }
            else{
                alta(v);
                bd.close();
            }
        }
        catch(Exception ex){
            Toast.makeText(this,ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return fila;
    }

    public void modificacion(View v){
        AdminBase admin = new AdminBase(this, "baseDeducirNro", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String dni = textViewDni.getText().toString();
        String nombre = textViewNombre.getText().toString();
        String cantIntentos = textViewIntentos.getText().toString();

        ContentValues registro = new ContentValues();

        String cantIntentosDB = "";
        Cursor datos;
        datos = bd.rawQuery("select cantIntentos from usuario where dni="+dni, null);
        if (datos.moveToFirst()) {
            cantIntentosDB = datos.getString(0);
        }

        int cantIntentosVieja = Integer.parseInt(cantIntentosDB);
        int cantIntentosNueva = Integer.parseInt(cantIntentos);
        if (cantIntentosVieja>cantIntentosNueva) {
            //datos nuevos
            registro.put("cantIntentos", cantIntentos);
            int cant = bd.update("usuario", registro, "dni=" + dni, null);
            bd.close();

            if (cant == 1) {
                Toast.makeText(this, "Datos del jugador cargados", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(this, "No se actualizaron datos, error de bd", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "puntaje peor al anterior, no se actualizaron", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
    }

    public void resetearTabla(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        AdminBase admin = new AdminBase(this, "baseDeducirNro", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.execSQL(clearDBQuery);
    }
}