package um.tp.juego_de_numeros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    private Button btnSalirScore;
    private ScrollView listaScore;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btnSalirScore = findViewById(R.id.btnSalirScore);
        btnSalirScore.setOnClickListener((v) -> { volver(v);});

        listaScore = findViewById(R.id.listaScore);
        layout = (LinearLayout)findViewById(R.id.layoutListaScore);

        layout.removeAllViewsInLayout();

        TextView subtitulo = new TextView(this);
        subtitulo.setText("DNI              Nombre          Cant Intentos");
        layout.addView(subtitulo);

        Cursor cursor = consulta();
        try {
            while (cursor.moveToNext()) {
                String aux = cursor.getString(0)+"        "+cursor.getString(1)+"           "+cursor.getString(2);
                TextView a = new TextView(this);
                a.setText(aux);
                layout.addView(a);
            }
        } finally {
            cursor.close();
        }
    }

    public Cursor consulta(){
        AdminBase admin = new AdminBase(this, "baseDeducirNro", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor datos = null;
        try{
            datos = bd.rawQuery("select * from usuario order by cantIntentos", null);
        }
        catch(Exception ex){
            Toast.makeText(this,ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return datos;
    }

    private void volver(View v){
        mostrarDialogo();
    }

    private void mostrarDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
        builder.setTitle("¿QUERÉS VOLVER A JUGAR?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"👍👍👍", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                        startActivity(intent);
                        //startActivity(getIntent());
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Cancelando...",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}