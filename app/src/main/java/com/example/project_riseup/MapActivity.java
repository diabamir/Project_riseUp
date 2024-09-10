package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MapActivity extends AppCompatActivity {

    private String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        location = "";


        // Find ImageViews by ID

        ImageView tabarias = findViewById(R.id.tabarias);
        ImageView karmiel = findViewById(R.id.karmiel);
        ImageView tamra = findViewById(R.id.tamra);
        ImageView nazareth = findViewById(R.id.nazareth);
        ImageView afula = findViewById(R.id.afula);
        ImageView karmil = findViewById(R.id.karmil);
        ImageView nahsholim = findViewById(R.id.nahsholim);
        ImageView hadera = findViewById(R.id.hadera);
        ImageView natanya = findViewById(R.id.natanya);
        ImageView ramatGan = findViewById(R.id.ramatGan);
        ImageView telAviv = findViewById(R.id.telAviv);
        ImageView ramla = findViewById(R.id.ramla);
        ImageView ashdod = findViewById(R.id.ashdod);
        ImageView ashqelon = findViewById(R.id.ashqelon);
        ImageView jerusalem = findViewById(R.id.jerusalem);
        ImageView betShemesh = findViewById(R.id.betShemesh);
        ImageView beersheba = findViewById(R.id.beersheba);
        ImageView yeroham = findViewById(R.id.yeroham);
        ImageView zin = findViewById(R.id.zin);
        ImageView yahoda = findViewById(R.id.yahoda);
        ImageView eilat = findViewById(R.id.eilat);
        ImageView reshonLezion = findViewById(R.id.reshonLezion);
        ImageView naharia = findViewById(R.id.naharia);
        ImageView akko = findViewById(R.id.akko);
        ImageView haifa = findViewById(R.id.haifa);
        ImageView qiryatShmona = findViewById(R.id.qiryatShmona);
        ImageView masadah = findViewById(R.id.masadah);



        setupImageViewClickListener(tabarias, "tabarias");
        setupImageViewClickListener(karmiel, "karmiel");
        setupImageViewClickListener(tamra, "tamra");
        setupImageViewClickListener(nazareth, "nazareth");
        setupImageViewClickListener(afula, "afula");
        setupImageViewClickListener(karmil, "karmil");
        setupImageViewClickListener(nahsholim, "nahsholim");
        setupImageViewClickListener(hadera, "hadera");
        setupImageViewClickListener(natanya, "natanya");
        setupImageViewClickListener(ramatGan, "ramatGan");
        setupImageViewClickListener(telAviv, "telAviv");
        setupImageViewClickListener(ramla, "ramla");
        setupImageViewClickListener(ashdod, "ashdod");
        setupImageViewClickListener(ashqelon, "ashqelon");
        setupImageViewClickListener(jerusalem, "jerusalem");
        setupImageViewClickListener(betShemesh, "betShemesh");
        setupImageViewClickListener(beersheba, "beersheba");
        setupImageViewClickListener(yeroham, "yeroham");
        setupImageViewClickListener(zin, "zin");
        setupImageViewClickListener(yahoda, "yahoda");
        setupImageViewClickListener(eilat, "eilat");
        setupImageViewClickListener(reshonLezion, "reshonLezion");
        setupImageViewClickListener(naharia, "naharia");
        setupImageViewClickListener(akko, "akko");
        setupImageViewClickListener(haifa, "haifa");
        setupImageViewClickListener(qiryatShmona, "qiryatShmona");
        setupImageViewClickListener(masadah, "masadah");
    }

    private void setupImageViewClickListener(ImageView imageView, String locationName) {
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, ViewGroupsActivity.class);
            intent.putExtra("LOCATION", locationName);
            startActivity(intent);
        });
    }
}