package um.tp.juego_de_numeros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private int emptyX=2;
    private int emptyY=2;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;
    public static int cantIntentos;
    private TextView movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToViews();

        cantIntentos = 0;
    }

    private void loadViews(){
        group=findViewById(R.id.group);
        buttons = new Button[3][3];

        for (int i = 0; i<group.getChildCount(); i++){
            buttons[i/3][i%3] = (Button) group.getChildAt(i);
        }
    }

    //Funcion loadNumbers: Carga el vector con los numeros del 1 al 8
    private void loadNumbers(){
        tiles = new int[9];
        for (int i = 0; i<group.getChildCount()-1; i++){
            tiles[i]=i+1;
        }
    }

    /* Funcion generateNumbers
       1) Seleccionamos un índice aleatorio de 0 a 7
       2) Intercambiamos el vector[i] con el elemento en el índice aleatorio (Función Shuffle de Java)*/
    private void generateNumbers(){
        int n = 8;
        Random random = new Random();
        while (n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum]=tiles[n];
            tiles[n]=temp;
        }
        if (!isSolvable())
        generateNumbers();
    }

    /* Funcion isSolvable
       1) Convertimos de rompecabezas 2-D a forma lineal.
       2) Contamos la cantidad de inversiones.
       3) Devuelve verdadero si el recuento de inversiones es par.*/
    private boolean isSolvable(){
        int countInversions=0;
        for (int i=0; i<8; i++){
            for (int j=0; j<i; j++) {
                if (tiles[j] < tiles[i])
                    countInversions++;
            }
        }
        return countInversions%2==0;
    }

    private void loadDataToViews(){
        emptyX=2;
        emptyY=2;
        for (int i=0;i<group.getChildCount()-1;i++){
            buttons[i/3][i%3].setText(String.valueOf(tiles[i]));
            buttons[i/3][i%3].setBackgroundResource(android.R.drawable.btn_default);
        }

        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this,R.color.colorFreeButton));

    }

    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        movimientos = findViewById(R.id.CantMovimientos);
        cantIntentos++;
        movimientos.setText(cantIntentos+"");

        if ((Math.abs(emptyX-x)==1&&emptyY==y)||(Math.abs(emptyY-y)==1&&emptyX==x)){ // verifica solo si empty es [2][2]
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFreeButton));
            emptyX=x;
            emptyY=y;
            checkWin();
        }

    }

    private void mostrarDialogoPuntaje(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("¿QUERÉS REGISTRAR TU PUNTAJE?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        irAPuntaje();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); mostrarDialogoBasico();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void mostrarDialogoBasico(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("¿QUERÉS VOLVER A JUGAR?");
        builder
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"👍👍👍", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Cancelando...",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void irAPuntaje() {
        Intent intent = new Intent(this, PuntajeActivity.class);
        startActivity(intent);
    }

    private void checkWin(){
        boolean isWin = false;
        if (emptyX==2&&emptyY==2){
            for (int i=0; i<group.getChildCount()-1;i++){
                if (buttons[i/3][i%3].getText().toString().equals(String.valueOf(i+1))){
                    isWin=true;
                }else{
                    isWin=false;
                    break;
                }
            }
        }
        if (isWin){
            Toast.makeText(this, "Win!!!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i<group.getChildCount();i++){
                buttons[i/3][i%3].setClickable(false); // para que no pueda seguir haciendo movimientos
            }
            mostrarDialogoPuntaje();
        }
    }

}