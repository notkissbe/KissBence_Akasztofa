package hu.notkissbe.akasztofa;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView betuk;
    private Button plusgomb;
    private Button minusgomb;
    private TextView kitalalando;
    private ImageView akasztoKep;
    private Button tippelgomb;
    private int megjelenitettBetu = 0;
    private String kitalalandoSzo = "";
    private String megjelenitettSzo = "";
    private int hibak = 0;
    private boolean gyozelem = false;


    String[] szavak = {
            "auto", "kocsi", "jármu", "alacsony", "felso",
            "szel", "skibidi", "peugeot", "citroen", "opel"
    };

    String[] betuklista = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        sorsolSzot();


        plusgomb.setOnClickListener(v -> {
            if(megjelenitettBetu == betuklista.length-1){
                megjelenitettBetu = 0;
            }
            else {
                megjelenitettBetu++;
            }
            megjelenit();
        });
        minusgomb.setOnClickListener(v -> {
            if(megjelenitettBetu == 0){
                megjelenitettBetu = betuklista.length-1;
            }
            else{
                megjelenitettBetu--;
            }
            megjelenit();
        });
        tippelgomb.setOnClickListener(v -> {
            tippel();
        });

    }

    private void megjelenit(){
        betuk.setText(betuklista[megjelenitettBetu]);
    }

    private void szoMegjelenit(){
        kitalalando.setText(megjelenitettSzo);
    }

    private void sorsolSzot(){
        Random r = new Random();
        kitalalandoSzo = szavak[r.nextInt(szavak.length)];
        for(int i = 0; i < kitalalandoSzo.length(); i++){
            megjelenitettSzo += "_";
        }
        szoMegjelenit();
    }

    private void tippel(){
        StringBuilder buffer = new StringBuilder();
        if(kitalalandoSzo.contains(betuklista[megjelenitettBetu])){
            for (int i = 0; i < kitalalandoSzo.length(); i++) {
                if(kitalalandoSzo.charAt(i) == betuklista[megjelenitettBetu].charAt(0)){
                    buffer.append(betuklista[megjelenitettBetu]);
                }
                else{
                    buffer.append(megjelenitettSzo.charAt(i));
                }
            }
        }
        else{
            buffer = new StringBuilder(megjelenitettSzo);
            hibazas();
        }
        megjelenitettSzo = buffer.toString();
        szoMegjelenit();

        if(hibak >= 13){
            //vesztes
            JatekVege();
        }
        else if(!megjelenitettSzo.contains("_")){
            //gyozelem
            gyozelem = true;
            JatekVege();

        }


    }

    private void hibazas() {

            hibak++;
            StringBuilder kepszam = new StringBuilder();
            kepszam.append("akasztofa");
            if (hibak < 10) {
                kepszam.append("0"+hibak);
            }
            else{
                kepszam.append(hibak);
            }
            String kepszamString = kepszam.toString();
            int resId = getResources().getIdentifier(kepszamString, "drawable", getPackageName());
            akasztoKep.setImageResource(resId);

    }
    private void ujjatek(){
        megjelenitettSzo = "";
        sorsolSzot();
        hibak = 0;
        akasztoKep.setImageResource(R.drawable.akasztofa00);
    }

    private void JatekVege(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(gyozelem){
            builder.setTitle("Győzelem!");
        }
        else {
            builder.setTitle("Vesztettél!");
        }
        builder.setMessage("Új játék?");
        builder.setPositiveButton("Igen", (dialog, which) -> {
            ujjatek();
        });
        builder.setNegativeButton("Nem", (dialog, which) -> {
            finish();
        });
        builder.show();
    }

    private void init() {
        betuk = findViewById(R.id.betuk);
        plusgomb = findViewById(R.id.plusgomb);
        minusgomb = findViewById(R.id.minusgomb);
        kitalalando = findViewById(R.id.kitalalando);
        akasztoKep = findViewById(R.id.akasztoKep);
        tippelgomb = findViewById(R.id.tippelgomb);
    }
}