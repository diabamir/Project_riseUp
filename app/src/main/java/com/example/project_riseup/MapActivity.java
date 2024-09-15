package com.example.project_riseup;



import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.SearchView;
import android.widget.Toast;

import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    private String location;
    private UserApi userApi;
    private long userId;

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

        Intent intent = getIntent();
        userId = intent.getLongExtra("USER_ID", -1);
        if (userId != -1) {
            // Initialize Retrofit and UserApi
            userApi = ApiClient.getClient().create(UserApi.class);
//            getUserById(userId);
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
//
//        if (userId != -1) {
//            User user = UserDatabase.getInstance(this).userDao().getUserById(userId);
//            if(!user.getSeeTheInstructions())
//            {
//                Intent intentt =new Intent(MapActivity.this, GroupExpActivity.class);
//                intent.putExtra("USER_ID", userId);
//                startActivity(intentt);
//            }
//        }



        location = "";
        // Initialize SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Find group by ID");


        // Set up SearchView listeners
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Check if the input is a valid number
                if (query.matches("\\d+")) {
                    // Start the ViewGroupById activity and pass the group ID
                    Intent intent = new Intent(MapActivity.this, ViewGroupsActivity.class);
                    intent.putExtra("GROUP_ID", Integer.parseInt(query));
                    startActivity(intent);
                    return true; // Indicate that the query has been handled
                } else {
                    // Show an error message if the input is not a valid ID
                    Toast.makeText(MapActivity.this, "Please enter a valid group ID", Toast.LENGTH_SHORT).show();
                    return false; // Indicate that the query was not handled
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: Handle the text change if needed, or just return false
                return false;
            }
        });


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
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
    }


//    private void getUserById(long userId) {
//        Call<User> call = userApi.getUserById(userId);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Toast.makeText(MapActivity.this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
//
//                if (response.isSuccessful()) {
//                    User user = response.body();
//                    // Handle the user object, e.g., display user details in the UI
////                    if (user != null) {
//                        // Check if the user has seen the instructions
////                        if (!user.getSeeTheInstructions()) {
////                            // Redirect to GroupExpActivity if getSeeTheInstructions is false
////                            Intent intent = new Intent(MapActivity.this, GroupExpActivity.class);
////                            intent.putExtra("USER_ID", userId);
////                            startActivity(intent);
//////                            finish();  // Optionally finish the current activity so the user can't navigate back
////                        } else {
////                            Toast.makeText(MapActivity.this, "User: " + user.getFirstName(), Toast.LENGTH_SHORT).show();
////                            // Proceed with the rest of your logic if needed
////                        }
////                    }
//                } else {
//                    Toast.makeText(MapActivity.this, "Failed to retrieve user", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(MapActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
//package com.example.project_riseup;
//
//
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import android.widget.SearchView;
//import android.widget.Toast;
//
//public class MapActivity extends AppCompatActivity {
//
//    private String location;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_map);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
////        Intent intent = getIntent();
////        int userId = intent.getIntExtra("USER_ID", -1);
////
////        if (userId != -1) {
////            User user = UserDatabase.getInstance(this).userDao().getUserById(userId);
////            if(!user.getSeeTheInstructions())
////            {
////                Intent intentt =new Intent(MapActivity.this, GroupExpActivity.class);
////                intent.putExtra("USER_ID", userId);
////                startActivity(intentt);
////            }
////        }
//
//
//
//        location = "";
//        // Initialize SearchView
//        SearchView searchView = findViewById(R.id.searchView);
//        searchView.setQueryHint("Find group by ID");
//
//
//        // Set up SearchView listeners
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Check if the input is a valid number
//                if (query.matches("\\d+")) {
//                    // Start the ViewGroupById activity and pass the group ID
//                    Intent intent = new Intent(MapActivity.this, ViewGroupsActivity.class);
//                    intent.putExtra("GROUP_ID", Integer.parseInt(query));
//                    startActivity(intent);
//                    return true; // Indicate that the query has been handled
//                } else {
//                    // Show an error message if the input is not a valid ID
//                    Toast.makeText(MapActivity.this, "Please enter a valid group ID", Toast.LENGTH_SHORT).show();
//                    return false; // Indicate that the query was not handled
//                }
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Optional: Handle the text change if needed, or just return false
//                return false;
//            }
//        });
//
//
//        // Find ImageViews by ID
//
//        ImageView tabarias = findViewById(R.id.tabarias);
//        ImageView karmiel = findViewById(R.id.karmiel);
//        ImageView tamra = findViewById(R.id.tamra);
//        ImageView nazareth = findViewById(R.id.nazareth);
//        ImageView afula = findViewById(R.id.afula);
//        ImageView karmil = findViewById(R.id.karmil);
//        ImageView nahsholim = findViewById(R.id.nahsholim);
//        ImageView hadera = findViewById(R.id.hadera);
//        ImageView natanya = findViewById(R.id.natanya);
//        ImageView ramatGan = findViewById(R.id.ramatGan);
//        ImageView telAviv = findViewById(R.id.telAviv);
//        ImageView ramla = findViewById(R.id.ramla);
//        ImageView ashdod = findViewById(R.id.ashdod);
//        ImageView ashqelon = findViewById(R.id.ashqelon);
//        ImageView jerusalem = findViewById(R.id.jerusalem);
//        ImageView betShemesh = findViewById(R.id.betShemesh);
//        ImageView beersheba = findViewById(R.id.beersheba);
//        ImageView yeroham = findViewById(R.id.yeroham);
//        ImageView zin = findViewById(R.id.zin);
//        ImageView yahoda = findViewById(R.id.yahoda);
//        ImageView eilat = findViewById(R.id.eilat);
//        ImageView reshonLezion = findViewById(R.id.reshonLezion);
//        ImageView naharia = findViewById(R.id.naharia);
//        ImageView akko = findViewById(R.id.akko);
//        ImageView haifa = findViewById(R.id.haifa);
//        ImageView qiryatShmona = findViewById(R.id.qiryatShmona);
//        ImageView masadah = findViewById(R.id.masadah);
//
//
//
//        setupImageViewClickListener(tabarias, "tabarias");
//        setupImageViewClickListener(karmiel, "karmiel");
//        setupImageViewClickListener(tamra, "tamra");
//        setupImageViewClickListener(nazareth, "nazareth");
//        setupImageViewClickListener(afula, "afula");
//        setupImageViewClickListener(karmil, "karmil");
//        setupImageViewClickListener(nahsholim, "nahsholim");
//        setupImageViewClickListener(hadera, "hadera");
//        setupImageViewClickListener(natanya, "natanya");
//        setupImageViewClickListener(ramatGan, "ramatGan");
//        setupImageViewClickListener(telAviv, "telAviv");
//        setupImageViewClickListener(ramla, "ramla");
//        setupImageViewClickListener(ashdod, "ashdod");
//        setupImageViewClickListener(ashqelon, "ashqelon");
//        setupImageViewClickListener(jerusalem, "jerusalem");
//        setupImageViewClickListener(betShemesh, "betShemesh");
//        setupImageViewClickListener(beersheba, "beersheba");
//        setupImageViewClickListener(yeroham, "yeroham");
//        setupImageViewClickListener(zin, "zin");
//        setupImageViewClickListener(yahoda, "yahoda");
//        setupImageViewClickListener(eilat, "eilat");
//        setupImageViewClickListener(reshonLezion, "reshonLezion");
//        setupImageViewClickListener(naharia, "naharia");
//        setupImageViewClickListener(akko, "akko");
//        setupImageViewClickListener(haifa, "haifa");
//        setupImageViewClickListener(qiryatShmona, "qiryatShmona");
//        setupImageViewClickListener(masadah, "masadah");
//    }
//
//    private void setupImageViewClickListener(ImageView imageView, String locationName) {
//        imageView.setOnClickListener(v -> {
//            Intent intent = new Intent(MapActivity.this, ViewGroupsActivity.class);
//            intent.putExtra("LOCATION", locationName);
//            startActivity(intent);
//        });
//    }
//}