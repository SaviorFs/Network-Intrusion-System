package com.nids;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class FirebaseLogger {

    private static boolean initialized = false;
    private static DatabaseReference dbRef;

    private static void initializeFirebase() {
        if (initialized) return;

        try {
            FileInputStream serviceAccount = new FileInputStream("nids-firebase-firebase-adminsdk-fbsvc-3e2746bec3.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://nids-firebase-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            dbRef = FirebaseDatabase.getInstance().getReference("alerts");

            initialized = true;
            System.out.println("Firebase initialized.");

        } catch (Exception e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
        }
    }

    public static void logThreat(String ip, String threatData) {
        initializeFirebase();

        if (dbRef == null) {
            System.err.println("Firebase database reference is null. Logging skipped.");
            return;
        }

        Map<String, Object> threat = new HashMap<>();
        threat.put("ip", ip);
        threat.put("message", threatData);
        threat.put("timestamp", Instant.now().toString());

        dbRef.push().setValueAsync(threat);
        System.out.println("Threat logged to Firebase.");
    }
}
