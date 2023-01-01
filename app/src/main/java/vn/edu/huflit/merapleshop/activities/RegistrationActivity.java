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
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.huflit.merapleshop.R;
import vn.edu.huflit.merapleshop.models.UserModel;

public class RegistrationActivity extends AppCompatActivity {

    Button signUp, back;
    EditText name, email, password, confirm;

    FirebaseAuth auth;
    FirebaseDatabase database;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressBar = findViewById(R.id.progessbar);
        progressBar.setVisibility(View.GONE);

        signUp = findViewById(R.id.btnConfirm);
        name = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassword);
        email = findViewById(R.id.edtUserEmail);
        back = findViewById(R.id.btnReturn);
        confirm = findViewById(R.id.edtConfirmPassword);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void createUser()
    {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String confirmPassword = confirm.getText().toString();

        if (TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, "User Name Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this, "User Email Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this, "Password Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword))
        {
            Toast.makeText(this, "Confirm Password Is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6)
        {
            Toast.makeText(this, "Password Length Must Be Greater Than 6 Letter",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //Create User
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            UserModel userModel = new UserModel(userName, userEmail, userPassword);
                            String id = task.getResult().getUser().getUid();

                            database.getReference().child("Users").child(id).setValue(userModel);

                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegistrationActivity.this, "Registration Is Successful",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegistrationActivity.this, "Error"+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}