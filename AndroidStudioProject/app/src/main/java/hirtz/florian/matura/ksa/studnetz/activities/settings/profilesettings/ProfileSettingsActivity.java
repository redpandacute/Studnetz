package hirtz.florian.matura.ksa.studnetz.activities.settings.profilesettings;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hirtz.florian.matura.ksa.studnetz.BuildConfig;
import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.settings.SettingsoverviewActivity;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.requests.BcryptSaveSettingsRequest;
import hirtz.florian.matura.ksa.studnetz.util.JSONtoInfo;
import hirtz.florian.matura.ksa.studnetz.util.SchoolMapper;
import hirtz.florian.matura.ksa.studnetz.util.TempFileGenerator;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ProfileSettingsActivity extends AppCompatActivity {

    private EditText firstname_et, name_et, description_et;
    private CheckBox german_cb, spanish_cb, french_cb, english_cb, biology_cb, music_cb, chemistry_cb, maths_cb, physics_cb;
    private Spinner school_sp, grade_sp;
    private Button save_bt;
    private FloatingActionButton changeprofilepicture_bt;
    private Bundle extras;
    private ImageView profilePicture_iv;

    private UserModel clientInfo;
    private ProfilePictureModel picture_big, picture_small;
    private File chosenFile, tempFile, tempDir;
    private Intent CameraIntent, GalleryIntent;
    private Uri imageURI, tempUri;

    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int QUALITY_BIG = 70, QUALITY_SMALL = 40;
    private static final int SCALE_BIG = 400, SCALE_SMALL = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        Toolbar toolbar = findViewById(R.id.profilesettings_toolbar);
        toolbar.setTitle(R.string.profileSettings_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePicture_iv = findViewById(R.id.profsettings_profilepic_imageview);

        firstname_et = findViewById(R.id.profsettings_firstname_edittext);
        name_et = findViewById(R.id.profsettings_name_edittext);
        description_et = findViewById(R.id.profsettings_description_edittext);

        school_sp = findViewById(R.id.profsettings_school_spinner);
        grade_sp = findViewById(R.id.profsettings_grade_spinner);

        german_cb = findViewById(R.id.profsettings_german_checkbox);
        spanish_cb = findViewById(R.id.profsettings_spanish_checkbox);
        english_cb = findViewById(R.id.profsettings_english_checkbox);
        french_cb = findViewById(R.id.profsettings_french_checkbox);
        biology_cb = findViewById(R.id.profsettings_biology_checkbox);
        music_cb = findViewById(R.id.profsettings_music_checkbox);
        chemistry_cb = findViewById(R.id.profsettings_chemics_checkbox);
        maths_cb = findViewById(R.id.profsettings_maths_checkbox);
        physics_cb = findViewById(R.id.profsettings_physics_checkbox);
        changeprofilepicture_bt = findViewById(R.id.profsettings_profilepicture_floatingactionbutton);

        changeprofilepicture_bt.setOnClickListener(new onChangeProfilePicture());
        save_bt = findViewById(R.id.profsettings_save_bt);
        save_bt.setOnClickListener(new onSaveListener());

        extras = getIntent().getExtras();
        try {
            clientInfo = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extras.getString("clientInfo")));
            firstname_et.setText(clientInfo.getFirstname());
            name_et.setText(clientInfo.getName());
            description_et.setText(clientInfo.getDescription());

            german_cb.setChecked(clientInfo.isGerman());
            spanish_cb.setChecked(clientInfo.isSpanish());
            english_cb.setChecked(clientInfo.isEnglish());
            french_cb.setChecked(clientInfo.isFrench());
            biology_cb.setChecked(clientInfo.isBiology());
            music_cb.setChecked(clientInfo.isMusic());
            chemistry_cb.setChecked(clientInfo.isChemistry());
            maths_cb.setChecked(clientInfo.isMaths());
            physics_cb.setChecked(clientInfo.isPhysics());

            picture_big = new ProfilePictureModel(getBaseContext(), new File(clientInfo.getTempProfilePicturePath()));
            profilePicture_iv.setImageBitmap(picture_big.getImageBitmap());

            new SchoolMapper(getBaseContext(), school_sp, grade_sp).startDisplay("schoollist.txt", clientInfo);
            grade_sp.setSelection(clientInfo.getGrade());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ProfileSettingsActivity.this, SettingsoverviewActivity.class);
                intent.putExtra("clientInfo", clientInfo.getJSON());
                startActivity(intent);
                System.out.println("::BACK BUTTON::");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == 0 && resultCode == RESULT_OK) {
            CropImage();
        } else if(requestCode == 1 && data != null) {
            imageURI = data.getData();
            CropImage();
        } else */
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                picture_big.updateWithPath(tempFile.getPath());
                picture_big.compress(QUALITY_BIG);
                picture_big.downscale(SCALE_BIG);
                profilePicture_iv.setImageBitmap(picture_big.getImageBitmap());
                String small_temp_path = new TempFileGenerator().getTempFilePath(getBaseContext(), picture_big.getBASE64());
                picture_small = new ProfilePictureModel(getBaseContext(), new File(small_temp_path));
                picture_small.compress(QUALITY_SMALL);
                picture_small.downscale(SCALE_SMALL);
                //System.out.println("BASE64" + picture_big.getBASE64());

                BcryptSaveSettingsRequest request = new BcryptSaveSettingsRequest(
                        clientInfo.getId(),
                        clientInfo.getFirstname(),
                        clientInfo.getName(),
                        clientInfo.getEmail(),
                        clientInfo.getSchool(),
                        clientInfo.getGrade(),
                        clientInfo.getDescription(),
                        clientInfo.getPasswordHash(),
                        clientInfo.isGerman(),
                        clientInfo.isSpanish(),
                        clientInfo.isEnglish(),
                        clientInfo.isFrench(),
                        clientInfo.isBiology(),
                        clientInfo.isChemistry(),
                        clientInfo.isMusic(),
                        clientInfo.isMaths(),
                        clientInfo.isPhysics(),
                        picture_big.getBASE64(),
                        picture_small.getBASE64(),
                        new onChangeProfilePictureListener()
                );

                RequestQueue request_queue = Volley.newRequestQueue(ProfileSettingsActivity.this); //Request Queue
                request_queue.add(request);
            }
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

                //https://inthecheesefactory.com/blog/how-to-share-access-to-file-with-fileprovider-on-android-nougat/en
                @Override
                public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                    if(!imageFiles.isEmpty() && imageFiles.size() == 1) {
                        imageURI = FileProvider.getUriForFile(ProfileSettingsActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                imageFiles.get(0));

                        CropImage(imageURI);
                    } else {
                        System.out.println("::ERROR WITH IMAGE::");
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission Granted");
                } else {
                    System.out.println("Permission Denied");
                }
        }
    }


    private void CropImage(Uri uri) {
        tempDir = getCacheDir();
        try {
            tempFile = File.createTempFile("file" + String.valueOf(System.currentTimeMillis()), ".png", tempDir);
        } catch (IOException e) {
            e.printStackTrace();
            tempFile = new File(tempDir, "file" + String.valueOf(System.currentTimeMillis())+ ".png");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            tempUri = Uri.fromFile(tempFile);
        } else {
            tempUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", tempFile);
        }

        try {
            Intent cropIntent = CropImage.activity(uri)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                    .setFixAspectRatio(true)
                    .setOutputUri(tempUri)
                    .setOutputCompressQuality(QUALITY_BIG)
                    .setRequestedSize(R.dimen.crop_dimen, R.dimen.crop_dimen, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                    .setAutoZoomEnabled(true)
                    .setActivityTitle(getString(R.string.crop_image_activity_title))
                    .setGuidelines(CropImageView.Guidelines.ON).getIntent(this);

            startActivityForResult(cropIntent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    //https://github.com/jkwiecien/EasyImage
    private void openChooserGalleryCamera() {
        EasyImage.openChooserWithGallery(ProfileSettingsActivity.this, getString(R.string.chooseOption_caption), 0);
    }

    private void getCameraPermission() {
        if(ContextCompat.checkSelfPermission(ProfileSettingsActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ProfileSettingsActivity.this, Manifest.permission.CAMERA)) {
                System.out.println("::CAMERA PERMISSION GRANTED::");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE);
                System.out.println("::CAMERA PERMISSION GRANTED::");
            }
        }
    }

    private void getStoragePermission() {
        if(ContextCompat.checkSelfPermission(ProfileSettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ProfileSettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                System.out.println("::R STORAGE PERMISSION GRANTED::");
            } else {
                ActivityCompat.requestPermissions(ProfileSettingsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                System.out.println("::R STORAGE PERMISSION GRANTED::");
            }
        }

        if(ContextCompat.checkSelfPermission(ProfileSettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ProfileSettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("::W STORAGE PERMISSION GRANTED::");
            } else {
                ActivityCompat.requestPermissions(ProfileSettingsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                System.out.println("::W STORAGE PERMISSION GRANTED::");
            }
        }
    }

    class onSaveListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String name = name_et.getText().toString();
            String firstname = firstname_et.getText().toString();
            String description = description_et.getText().toString();
            System.out.println("DESC: " + description);

            String school = school_sp.getSelectedItem().toString();


            int grade = 0;
            if(grade_sp != null && grade_sp.getSelectedItem() != null) {
                grade = Integer.parseInt(grade_sp.getSelectedItem().toString());
            }

            boolean german = german_cb.isChecked();
            boolean spanish = spanish_cb.isChecked();
            boolean english = english_cb.isChecked();
            boolean french = french_cb.isChecked();
            boolean biology = biology_cb.isChecked();
            boolean chemistry = chemistry_cb.isChecked();
            boolean maths = maths_cb.isChecked();
            boolean physics = physics_cb.isChecked();
            boolean music = music_cb.isChecked();

            if (!name.isEmpty() && !firstname.isEmpty() && school_sp != null && school_sp.getSelectedItem() != null) {

                System.out.println("Making save request");

                BcryptSaveSettingsRequest save_request;

                save_request = new BcryptSaveSettingsRequest(clientInfo.getId(),
                        firstname,
                        name,
                        clientInfo.getEmail(),
                        school,
                        grade,
                        description,
                        clientInfo.getPasswordHash(),
                        german,
                        spanish,
                        english,
                        french,
                        biology,
                        chemistry,
                        music,
                        maths,
                        physics,
                        new SettingsoverviewActivity.onSaveResponseListener(getBaseContext(), 0)
                );

                RequestQueue request_queue = Volley.newRequestQueue(ProfileSettingsActivity.this); //Request Queue
                request_queue.add(save_request);
            }
        }
    }

    class onChangeProfilePicture implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            getCameraPermission();
            getStoragePermission();
            openChooserGalleryCamera();
        }
    }

    class onChangeProfilePictureListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsn = new JSONObject(response);
                System.out.println("PD CHANGE:: " + response);
                if(jsn.getBoolean("success")) {
                    clientInfo.setTempProfilePicturePath(tempFile.getPath());
                    clientInfo.updateJSON();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

