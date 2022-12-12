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
    private Button button_start_game_n3;
    private Button button_start_game_n2;
    private Button button_start_game_n1;

    //nivel 3= 8 - nivel 2= 6 - nivel 1 = 4
    public static int nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button_start_game_n3=findViewById(R.id.button_start_game_n3);
        button_start_game_n2=findViewById(R.id.button_start_game_n2);
        button_start_game_n1=findViewById(R.id.button_start_game_n1);

        button_start_game_n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel=4;
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        button_start_game_n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel=6;
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        button_start_game_n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivel=8;
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });

    }
}