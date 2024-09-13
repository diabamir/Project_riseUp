//package com.example.project_riseup;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MainActivity extends AppCompatActivity {
//    private Button b;
//    private Button groups;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        b=findViewById(R.id.button2);
//        b.setOnClickListener(v -> {
//            Intent intent = new Intent(this, firstPageIcon.class);
//            startActivity(intent);
//        });
//
//
//        groups=findViewById(R.id.groups);
//        groups.setOnClickListener(v -> {
//            Intent intent = new Intent(this, MapActivity.class);
//            startActivity(intent);
//        });
//
//    }
//}
package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button b;
    private Button groups;
    private UserRepository userRepository;

    Button join;
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

        b=findViewById(R.id.button2);
        b.setOnClickListener(v -> {
            Intent intent = new Intent(this, firstPageIcon.class);
            startActivity(intent);
        });
        groups=findViewById(R.id.groups);
        groups.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
        userRepository = new UserRepository(this);

//        join=findViewById(R.id.join);
//        join.setOnClickListener(v -> {
//            Intent intent = new Intent(this, UserGroups.class);
//            startActivity(intent);
//        });


    }
}