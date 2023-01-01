package vn.edu.huflit.merapleshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vn.edu.huflit.merapleshop.MainActivity;
import vn.edu.huflit.merapleshop.R;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progessbar);

        if (auth.getCurrentUser() != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            Toast.makeText(this, "Welcome to Meraple Shop", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}