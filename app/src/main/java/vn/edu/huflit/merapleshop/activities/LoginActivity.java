package vn.edu.huflit.merapleshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.edu.huflit.merapleshop.R;

public class LoginActivity extends AppCompatActivity {

    Button singIn, signUp;
    EditText email, password;

    FirebaseAuth auth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progessbar);
        progressBar.setVisibility(View.GONE);

        singIn = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnRegist);
        email = findViewById(R.id.edtUserEmail);
        password = findViewById(R.id.edtPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

                progressBar.setVisibility(View.VISIBLE);
            }
        });

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginUser();

                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "User Email Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password Length Must Be Greater Than 6 Letter",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Login User
        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(LoginActivity.this, "Login Is Successful",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(LoginActivity.this, "Error"+task.isSuccessful(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}