package com.example.project_riseup;
//package com.example.israa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FavActivites extends AppCompatActivity {
    private String selectedActivity = "";
    private TextView lastSelectedTextView = null; // To keep track of last selected TextView

    private long userId;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fav_activites);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Intent intentt = getIntent();
        if (intentt != null) {
            if (intentt.hasExtra("LOCATION")) {
                location = intentt.getStringExtra("LOCATION");
            }
            if (intentt.hasExtra("USER_ID")) {
                userId = intentt.getLongExtra("USER_ID", -1);
                if (userId == -1) {
                    Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show();
                }
            }
        }




        ImageView tealSquare = findViewById(R.id.tealSquare);
        ImageView purpleSquare = findViewById(R.id.purpleSquare);
        ImageView cartWheeling = findViewById(R.id.cartWheeling);
        ImageView greenSquare = findViewById(R.id.greenSquare);
        ImageView weightLifting = findViewById(R.id.weightLifting);
        ImageView blueSquare = findViewById(R.id.blueSquare);
        ImageView yoga = findViewById(R.id.yoga);
        ImageView darkOrange = findViewById(R.id.darkOrange);
        ImageView swimming = findViewById(R.id.swimming);
        ImageView lightYellowSquare = findViewById(R.id.lightYellowSquare);
        ImageView biking = findViewById(R.id.biking);
        ImageView pinkSquare = findViewById(R.id.pinkSquare);
        ImageView wrestling = findViewById(R.id.wrestiling);
        ImageView skyBlue = findViewById(R.id.skyBlue);
        ImageView handBall = findViewById(R.id.handBall);
        ImageView limeGreen = findViewById(R.id.limeGreen);
        ImageView climbing = findViewById(R.id.climbing);
        ImageView orange = findViewById(R.id.orange);
        ImageView surfing = findViewById(R.id.surfing);
        ImageView lightRed = findViewById(R.id.lightRed);
        ImageView golfing = findViewById(R.id.golfing);
        ImageView bordeaux = findViewById(R.id.bordeaux);
        ImageView waterPolo = findViewById(R.id.waterPolo);
        ImageView lightPurple = findViewById(R.id.lightPurple);
        ImageView oliveSquare = findViewById(R.id.oliveSquare);
        ImageView football = findViewById(R.id.football);
        ImageView lightPink = findViewById(R.id.lightPink);
        ImageView basketball = findViewById(R.id.basketball);
        ImageView lightBlue = findViewById(R.id.lightBlue);
        ImageView rowing = findViewById(R.id.rowing);
        ImageView horseRacing = findViewById(R.id.horseRacing);
        ImageView navyBlue = findViewById(R.id.navyBlue);
        ImageView bowling = findViewById(R.id.bowling);
        ImageView yellow = findViewById(R.id.yellow);
        ImageView pingpong = findViewById(R.id.pingpong);
        ImageView darkPink = findViewById(R.id.darkPink);
        ImageView volleyball = findViewById(R.id.vollyball);
        ImageView turkis = findViewById(R.id.turkis);
        ImageView tennis = findViewById(R.id.tennis);
        ImageView running = findViewById(R.id.running);
        Button continuee = findViewById(R.id.continuee1);

        TextView WeightliftingText = findViewById(R.id.WeightliftingText);
        TextView yogatext = findViewById(R.id.yogatext);
        TextView Cartwheelingtext = findViewById(R.id.Cartwheelingtext);
        TextView RunningText = findViewById(R.id.RunningText);
        TextView WaterPoloText = findViewById(R.id.WaterPoloText);
        TextView SurfingText = findViewById(R.id.SurfingText);
        TextView golfingText = findViewById(R.id.golfingText);
        TextView ClimbingText = findViewById(R.id.ClimbingText);
        TextView BasketballText = findViewById(R.id.BasketballText);
        TextView HorseRacingText = findViewById(R.id.HorseRacingText);
        TextView FootballText = findViewById(R.id.FootballText);
        TextView RowingText = findViewById(R.id.RowingText);
        // Inside your Activity or Fragment's onCreate method
        TextView PingPongText = findViewById(R.id.PingPongText);
        TextView BowlingText = findViewById(R.id.BowlingText);
        TextView TennisText = findViewById(R.id.TennisText);
        TextView Volleyball = findViewById(R.id.Volleyball);

        TextView WrestlingText = findViewById(R.id.WrestlingText);
        TextView HandballText = findViewById(R.id.HandballText);
        TextView SwimmingText = findViewById(R.id.SwimmingText);
        TextView BikingText = findViewById(R.id.BikingText);


        // Define the default color and green color
        int defaultColor = getResources().getColor(android.R.color.black);
        int greenColor = getResources().getColor(android.R.color.holo_green_dark);

        // Set click listeners for each ImageView
        tealSquare.setOnClickListener(view -> updateTextColor(tealSquare, WeightliftingText, defaultColor, greenColor,"weightLifting"));
        weightLifting.setOnClickListener(view -> updateTextColor(weightLifting, WeightliftingText, defaultColor, greenColor,"weightLifting"));
        yoga.setOnClickListener(view -> updateTextColor(yoga, yogatext, defaultColor, greenColor,"yoga"));
        blueSquare.setOnClickListener(view -> updateTextColor(blueSquare, yogatext, defaultColor, greenColor,"yoga"));
        purpleSquare.setOnClickListener(view -> updateTextColor(purpleSquare, Cartwheelingtext, defaultColor, greenColor,"cartWheeling"));
        cartWheeling.setOnClickListener(view -> updateTextColor(cartWheeling, Cartwheelingtext, defaultColor, greenColor,"cartWheeling"));
        greenSquare.setOnClickListener(view -> updateTextColor(greenSquare, RunningText, defaultColor, greenColor,"running"));
        running.setOnClickListener(view -> updateTextColor(greenSquare, RunningText, defaultColor, greenColor,"running"));
        swimming.setOnClickListener(view -> updateTextColor(swimming, SwimmingText, defaultColor, greenColor,"swimming"));
        darkOrange.setOnClickListener(view -> updateTextColor(darkOrange, SwimmingText, defaultColor, greenColor,"swimming"));
        lightYellowSquare.setOnClickListener(view -> updateTextColor(lightYellowSquare, BikingText, defaultColor, greenColor,"biking"));
        biking.setOnClickListener(view -> updateTextColor(biking, BikingText, defaultColor, greenColor,"biking"));
        pinkSquare.setOnClickListener(view -> updateTextColor(pinkSquare, WrestlingText, defaultColor, greenColor,"wrestling"));
        wrestling.setOnClickListener(view -> updateTextColor(wrestling, WrestlingText, defaultColor, greenColor,"wrestling"));
        skyBlue.setOnClickListener(view -> updateTextColor(skyBlue, HandballText, defaultColor, greenColor,"handBall"));
        handBall.setOnClickListener(view -> updateTextColor(handBall, HandballText, defaultColor, greenColor,"handBall"));
        limeGreen.setOnClickListener(view -> updateTextColor(limeGreen, ClimbingText, defaultColor, greenColor,"climbing"));
        climbing.setOnClickListener(view -> updateTextColor(climbing, ClimbingText, defaultColor, greenColor,"climbing"));
        orange.setOnClickListener(view -> updateTextColor(orange, SurfingText, defaultColor, greenColor,"surfing"));
        surfing.setOnClickListener(view -> updateTextColor(surfing, SurfingText, defaultColor, greenColor,"surfing"));
        lightRed.setOnClickListener(view -> updateTextColor(lightRed, golfingText, defaultColor, greenColor,"golfing"));
        golfing.setOnClickListener(view -> updateTextColor(golfing, golfingText, defaultColor, greenColor,"golfing"));
        bordeaux.setOnClickListener(view -> updateTextColor(bordeaux, WaterPoloText, defaultColor, greenColor,"waterPolo"));
        waterPolo.setOnClickListener(view -> updateTextColor(waterPolo, WaterPoloText, defaultColor, greenColor,"waterPolo"));
        lightPurple.setOnClickListener(view -> updateTextColor(lightPurple, HorseRacingText, defaultColor, greenColor,"horseRacing"));
        horseRacing.setOnClickListener(view -> updateTextColor(horseRacing, HorseRacingText, defaultColor, greenColor,"horseRacing"));
        oliveSquare.setOnClickListener(view -> updateTextColor(oliveSquare, FootballText, defaultColor, greenColor,"football"));
        football.setOnClickListener(view -> updateTextColor(football, FootballText, defaultColor, greenColor,"football"));
        lightPink.setOnClickListener(view -> updateTextColor(lightPink, BasketballText, defaultColor, greenColor,"basketball"));
        basketball.setOnClickListener(view -> updateTextColor(basketball, BasketballText, defaultColor, greenColor,"basketball"));
        navyBlue.setOnClickListener(view -> updateTextColor(navyBlue, BowlingText, defaultColor, greenColor,"bowling"));
        bowling.setOnClickListener(view -> updateTextColor(bowling, BowlingText, defaultColor, greenColor,"bowling"));
        turkis.setOnClickListener(view -> updateTextColor(turkis, TennisText, defaultColor, greenColor,"tennis"));
        tennis.setOnClickListener(view -> updateTextColor(tennis, TennisText, defaultColor, greenColor,"tennis"));
        yellow.setOnClickListener(view -> updateTextColor(yellow, PingPongText, defaultColor, greenColor,"pingpong"));
        pingpong.setOnClickListener(view -> updateTextColor(pingpong, PingPongText, defaultColor, greenColor,"pingpong"));
        darkPink.setOnClickListener(view -> updateTextColor(darkPink, Volleyball, defaultColor, greenColor,"volleyball"));
        volleyball.setOnClickListener(view -> updateTextColor(volleyball, Volleyball, defaultColor, greenColor,"volleyball"));
        lightBlue.setOnClickListener(view -> updateTextColor(lightBlue, RowingText, defaultColor, greenColor,"rowing"));
        rowing.setOnClickListener(view -> updateTextColor(rowing, RowingText, defaultColor, greenColor,"rowing"));



        continuee.setOnClickListener(view -> {
            // Check if an activity was selected
            if (!selectedActivity.isEmpty()) {
                // Create an Intent to start the next activity
                Intent intent = new Intent(FavActivites.this, AddGroupActivity.class); // Replace PageXActivity with your target activity
                intent.putExtra("selectedActivity", selectedActivity); // Pass the selected activity string
                intent.putExtra("LOCATION", location);
                intent.putExtra("USER_ID", userId);
                startActivity(intent); // Start the next activity
            }
        });
    }

    // Helper method to update the TextView color
    private void updateTextColor(ImageView imageView, TextView textView, int defaultColor, int greenColor, String activityName) {
        // Reset the color of the last selected TextView
        if (lastSelectedTextView != null) {
            lastSelectedTextView.setTextColor(defaultColor);
        }
        // Set the color of the current TextView to green
        textView.setTextColor(greenColor);

        selectedActivity = activityName;
        // Update the lastSelectedTextView to the current one
        lastSelectedTextView = textView;
    }
}