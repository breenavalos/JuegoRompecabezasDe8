package um.tp.juego_de_numeros;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import um.tp.juego_de_numeros.databinding.ActivityMainBinding;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button botonComenzarFacil;
    private Button botonComenzarDificil;
    //Falso -> Facil / Verdadero-> Dificil
    public static boolean bandera=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        botonComenzarDificil=findViewById(R.id.button_start_game_dif);
        botonComenzarFacil=findViewById(R.id.button_start_game_ea);

        botonComenzarFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bandera queda en falso.
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        botonComenzarDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=true;
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });

    }
}