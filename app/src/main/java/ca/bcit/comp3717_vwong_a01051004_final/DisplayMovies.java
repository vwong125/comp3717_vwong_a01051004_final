package ca.bcit.comp3717_vwong_a01051004_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DisplayMovies extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "Get Movies";
    ArrayList<String> movies = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        db.collection("Movie")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                StringBuilder sb = new StringBuilder();
                                sb.append(String.format("Movie Title: %s\n", document.get("movie_title")));
                                sb.append(String.format("Description: %s\n", document.get("description")));
                                if (!document.get("info_link").toString().equals("")) {
                                    sb.append(String.format("Info Link: %s\n", document.get("info_link")));
                                }

                                movies.add(sb.toString());
                            }

                            Log.d(TAG, movies.toString());
                            ArrayAdapter adapter = new ArrayAdapter(DisplayMovies.this, R.layout.list_view_item);
                            adapter.addAll(movies);
                            ListView lv = findViewById(R.id.movies);
                            lv.setAdapter(adapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void back(View view) {
        finish();
    }
}
