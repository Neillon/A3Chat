package com.neillon.a3chat.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.neillon.a3chat.R;
import com.neillon.a3chat.configuration.FirestorageConfiguration;
import com.neillon.a3chat.dao.UserDao;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class PreferencesFragment extends Fragment {

    public String originalNickname;

    private Button cancel, saveOrEdit;
    public ImageView imgProfile;
    public EditText nickname;
    private ProgressBar spinner;

    private Uri imageUri;

    public DocumentReference reference;
    private SharedPreferences sharedPreferences;

    public boolean edit = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preferences_fragment, container, false);

        this.imgProfile = (ImageView) rootView.findViewById(R.id.img_profile);
        this.nickname = (EditText) rootView.findViewById(R.id.txt_nickname_profile);
        this.cancel = (Button) rootView.findViewById(R.id.btn_cancel_profile);
        this.saveOrEdit = (Button) rootView.findViewById(R.id.btn_save_or_edit_profile);
        this.spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);

        getUser();
        editEvent();

        this.imgProfile.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (edit) {
                    Intent photoPickerIntent = new Intent("android.intent.action.PICK");
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 100);
                }
            }
        });
        this.cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                edit = false;
                editEvent();
            }
        });
        this.saveOrEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (edit) {
                    saveChangesUser();
                    return;
                }
                edit = true;
                editEvent();
            }
        });
        return rootView;
    }

    public String getNickname() {
        this.sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preferences_key), 0);
        return this.sharedPreferences.getString(getString(R.string.nickname_key), "");
    }

    public boolean stayConnected() {
        this.sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preferences_key), 0);
        return this.sharedPreferences.getBoolean(getString(R.string.stay_connected), true);
    }

    private void savePreferences(String nickname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.nickname_key), nickname);
        editor.apply();
    }

    public void getUser() {
        UserDao.getUsers(getNickname()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult().getDocuments().size() > 0) {
                    reference = task.getResult().getDocuments().get(0).getReference();
                    String image = task.getResult().getDocuments().get(0).getString("profile_image");
                    originalNickname = task.getResult().getDocuments().get(0).getString("nickname");
                    nickname.setText(originalNickname);
                    if (image == null || image.isEmpty() || image.equals("")) {
                        image = getString(R.string.default_profile_image);
                    }
                    Glide.with(getActivity()).load(image).into(imgProfile);
                }
            }
        });
    }

    public void saveChangesUser() {
        spinner.setVisibility(View.VISIBLE);
        if (this.originalNickname != null && !this.originalNickname.equalsIgnoreCase(this.nickname.getText().toString())) {
            UserDao.getUsers(this.nickname.getText().toString()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                public void onComplete(Task<QuerySnapshot> task) {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    if (task.getResult().getDocuments().size() == 0) {
                        uploadImage();
                    } else {
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "O nickname já está sendo utilizado.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            uploadImage();
        }
    }

    public void editEvent() {
        this.nickname.setEnabled(this.edit);
        this.cancel.setVisibility(this.edit ? View.VISIBLE : View.INVISIBLE);
        this.saveOrEdit.setText(this.edit ? "Save" : "Edit");
    }

    public void uploadImage() {
        if (this.imageUri != null) {
            final StorageReference storageReference = FirestorageConfiguration.getInstance().getReference().child("profiles/" + System.currentTimeMillis());;
            storageReference.putFile(this.imageUri).continueWithTask(new Continuation<TaskSnapshot, Task<Uri>>() {
                public Task<Uri> then(Task<TaskSnapshot> task) throws Exception {
                    if (task.isSuccessful()) {
                        return storageReference.getDownloadUrl();
                    }
                    throw task.getException();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                public void onComplete(Task<Uri> task) {
                    if (task.isSuccessful()) {
                        updateUser((Uri) task.getResult());
                    } else {
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Erro ao fazer upload da imagem", Toast.LENGTH_LONG).show();
                    }
                }
            });
            return;
        }
        updateUser(null);
    }

    public void updateUser(Uri profileImage) {
        Map<String, Object> user = new HashMap<>();
        user.put("nickname", this.nickname.getText().toString());
        user.put("profile_image", profileImage != null ? profileImage.toString() : null);
        this.reference.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                task.getResult();
                edit = false;
                originalNickname = nickname.getText().toString();
                if(stayConnected()) {
                    savePreferences(originalNickname);
                }
                editEvent();
                spinner.setVisibility(View.GONE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            try {
                this.imageUri = data.getData();
                this.imgProfile.setImageBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(this.imageUri)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Algo deu errado", Toast.LENGTH_LONG).show();
            }
        }
    }
}
