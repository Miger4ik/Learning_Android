package com.example.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final int RC_IMAGE_PICKER = 123;

    private ListView messageListView;
    private AwesomeMessageAdapter adapter;

    private List<AwesomeMessage> awesomeMessages;

    private Button sendMessageButton;
    private EditText messageEditText;
    private ImageButton sendImageButton;
    private ProgressBar progressBar;

    private String userName;

    private FirebaseDatabase database;
    private DatabaseReference messageDatabaseReference;
    private ChildEventListener messageChildEventListener;

    private DatabaseReference usersDatabaseReference;
    private ChildEventListener usersChildEventListener;

    private FirebaseStorage storage;
    private StorageReference chatImagesStorageReference;

    private String recipientUserId;
    private String senderUserId;
    private String recipientUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findAllViews();
        stopProgressBar();
        setOnClickListeners();
        setChangeListeners();

        Intent intent = getIntent();

        if (intent == null) {
            recipientUserId = "";
            senderUserId = "";
            recipientUserName = "";
        } else {
            recipientUserId = intent.getStringExtra("recipientUserId");
            senderUserId = intent.getStringExtra("senderUserId");
            recipientUserName = intent.getStringExtra("recipientUserName");
        }

        setTitle("Chat with " + recipientUserName);

        database = FirebaseDatabase.getInstance();
        messageDatabaseReference = database.getReference().child("messages");
        usersDatabaseReference = database.getReference().child("users");

        storage = FirebaseStorage.getInstance();
        chatImagesStorageReference = storage.getReference().child("chat_images");

        awesomeMessages = new ArrayList<>();

        adapter = new AwesomeMessageAdapter(this, R.layout.message_item, awesomeMessages);
        messageListView.setAdapter(adapter);

        usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(user.getId())) {
                    userName = user.getName();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        messageChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AwesomeMessage message = snapshot.getValue(AwesomeMessage.class);

                if (message.getSender().equals(senderUserId) && message.getRecipient().equals(recipientUserId)) {
                    message.setMine(true);
                    adapter.add(message);
                } else if (message.getSender().equals(recipientUserId) && message.getRecipient().equals(senderUserId)) {
                    message.setMine(false);
                    adapter.add(message);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        messageDatabaseReference.addChildEventListener(messageChildEventListener);
        usersDatabaseReference.addChildEventListener(usersChildEventListener);
    }

    private void findAllViews() {
        sendMessageButton = findViewById(R.id.sendMessageButton);
        messageEditText = findViewById(R.id.messageEditText);
        sendImageButton = findViewById(R.id.sendPhotoButton);
        progressBar = findViewById(R.id.progressBar);
        messageListView = findViewById(R.id.messageListView);
    }

    private void setOnClickListeners() {
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send message
                AwesomeMessage message = new AwesomeMessage();
                message.setText(messageEditText.getText().toString());
                message.setName(userName);
                message.setSender(senderUserId);
                message.setRecipient(recipientUserId);
                message.setImageUrl(null);
                messageDatabaseReference.push().setValue(message);
                // clear EditText
                messageEditText.setText("");
            }
        });

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), RC_IMAGE_PICKER);

            }
        });
    }

    private void setChangeListeners() {
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendMessageButton.setEnabled(true);
                } else {
                    sendMessageButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // не більше 500 символів в одному повідомленні
        messageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
    }

    private void startProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void stopProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChatActivity.this, SignInActivity.class));
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            final StorageReference imageReference = chatImagesStorageReference.child(selectedImageUri.getLastPathSegment());
            UploadTask uploadTask = imageReference.putFile(selectedImageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        AwesomeMessage message = new AwesomeMessage();
                        message.setImageUrl(downloadUri.toString());
                        message.setName(userName);
                        message.setSender(senderUserId);
                        message.setRecipient(recipientUserId);
                        messageDatabaseReference.push().setValue(message);
                    } else {

                    }
                }
            });
        }
    }
}