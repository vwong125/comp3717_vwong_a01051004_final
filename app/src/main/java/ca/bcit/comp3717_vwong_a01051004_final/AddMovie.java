package ca.bcit.comp3717_vwong_a01051004_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMovie extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "Movie Insertion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
    }

    public void addToDatabase(View view) {
        EditText movie_title = findViewById(R.id.movie_title);
        final String movie_text = movie_title.getText().toString();

        EditText description = findViewById(R.id.description);
        String description_text = description.getText().toString();

        EditText info_link = findViewById(R.id.info_link);
        String info_link_text = info_link.getText().toString();

        Map<String, Object> movie = new HashMap<>();

        movie.put("movie_title", movie_text);
        movie.put("description", description_text);
        movie.put("info_link", info_link_text);

        if(movie_text.equals("") || description_text.equals("")) {
            Toast toast = Toast.makeText(AddMovie.this, "Empty Movie Title or Description", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            db.collection("Movie")
                    .add(movie)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast toast = Toast.makeText(AddMovie.this, String.format("Added Movie: %s", movie_text), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    public void back(View view) {
        finish();
    }
}
