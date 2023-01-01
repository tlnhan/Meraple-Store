package vn.edu.huflit.merapleshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.merapleshop.R;
import vn.edu.huflit.merapleshop.adapter.NavCategoryAdapter;
import vn.edu.huflit.merapleshop.adapter.NavCategoryDetailedAdapter;
import vn.edu.huflit.merapleshop.models.HomeCategory;
import vn.edu.huflit.merapleshop.models.NavCategoryDetailedModel;
import vn.edu.huflit.merapleshop.models.ViewAllModel;

public class NavCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        String type = getIntent().getStringExtra("type");
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.nav_cat_det_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progessbar);
        progressBar.setVisibility(View.VISIBLE);

        //Getting shirt
        if (type != null && type.equalsIgnoreCase("black")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "black")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        NavCategoryDetailedModel viewAllModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                        list.add(viewAllModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }

    }
}