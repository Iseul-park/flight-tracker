package algonquin.cst2335.flighttracker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.flighttracker.databinding.ActivityMainBinding;
import algonquin.cst2335.flighttracker.flight.FlightMainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button enterButton;
    private EditText usernameEditText;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        enterButton = binding.enterButton;
        usernameEditText = binding.userName;

        prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Restore the previously saved username from SharedPreferences, if available
        String savedUsername = prefs.getString("username", "");
        usernameEditText.setText(savedUsername);

        enterButton.setOnClickListener(click -> {
            String username = usernameEditText.getText().toString().trim();

            if (!username.isEmpty()) {
                // Store the entered username in SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, FlightMainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        // Load the GIF image using Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.flight_logo)
                .into(new ImageViewTarget<GifDrawable>(binding.flightLogo) {
                    @Override
                    protected void setResource(@Nullable GifDrawable resource) {
                        binding.flightLogo.setImageDrawable(resource);
                    }
                });
    }

}