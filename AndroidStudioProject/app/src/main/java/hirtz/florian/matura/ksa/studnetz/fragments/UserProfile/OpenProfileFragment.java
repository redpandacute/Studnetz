package hirtz.florian.matura.ksa.studnetz.fragments.UserProfile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.File;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.requests.BigProfilePictureRequest;


public class OpenProfileFragment extends Fragment {

    private MainActivity mActivity;
    private UserModel mUserModel;
    private ProfilePictureModel mPicture;

    private ImageView profilePicture_iv;

    public OpenProfileFragment() {
        //empty constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
        mUserModel = mActivity.getOpenprofileModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        Toolbar toolbar = view.findViewById(R.id.openprofile_toolbar);
        toolbar.setTitle(mUserModel.getFirstname() + " " + mUserModel.getName());
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView name_tv = view.findViewById(R.id.openprofile_name_textview);
        name_tv.setText(mUserModel.getFirstname() + " " + mUserModel.getName());

        TextView school_tv = view.findViewById(R.id.openprofile_school_textview);
        school_tv.setText(mUserModel.getSchool() + mUserModel.getStringGrade());

        TextView desc_tv = view.findViewById(R.id.openprofile_description_textview);
        desc_tv.setText(mUserModel.getDescription());

        Button openChatButton = view.findViewById(R.id.openprofile_openchatbutton_button);
        openChatButton.setOnClickListener(new OnOpenChatListener(this));

        setHasOptionsMenu(true);

        //EMBLEMS::
        ImageView math_medal = view.findViewById(R.id.openprofile_math_imageview);
        if (!mUserModel.isMaths()) {
            math_medal.setVisibility(View.GONE);
        }

        ImageView spanish_medal = view.findViewById(R.id.openprofile_spanish_imageview);
        if (!mUserModel.isSpanish()) {
            spanish_medal.setVisibility(View.GONE);
        }

        ImageView physics_medal = view.findViewById(R.id.openprofile_physics_imageview);
        if (!mUserModel.isPhysics()) {
            physics_medal.setVisibility(View.GONE);
        }

        ImageView german_medal = view.findViewById(R.id.openprofile_german_imageview);
        if (!mUserModel.isGerman()) {
            german_medal.setVisibility(View.GONE);
        }

        ImageView biology_medal = view.findViewById(R.id.openprofile_biology_imageview);
        if (!mUserModel.isBiology()) {
            biology_medal.setVisibility(View.GONE);
        }

        ImageView chemistry_medal = view.findViewById(R.id.openprofile_chemistry_imageview);
        if (!mUserModel.isChemistry()) {
            chemistry_medal.setVisibility(View.GONE);
        }

        ImageView music_medal = view.findViewById(R.id.openprofile_music_imageview);
        if (!mUserModel.isMusic()) {
            music_medal.setVisibility(View.GONE);
        }

        ImageView french_medal = view.findViewById(R.id.openprofile_french_imageview);
        if (!mUserModel.isFrench()) {
            french_medal.setVisibility(View.GONE);
        }

        ImageView english_medal = view.findViewById(R.id.openprofile_english_imageview);
        if (!mUserModel.isEnglish()) {
            english_medal.setVisibility(View.GONE);
        }

        profilePicture_iv = view.findViewById(R.id.openprofile_profilepicture_imageview);
        profilePicture_iv.setImageBitmap(mActivity.getOpenprofilePicture().getImageBitmap());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBigProfilePicture();
    }

    private void getBigProfilePicture() {

        BigProfilePictureRequest request = new BigProfilePictureRequest(mUserModel.getId(), new OnBigPictureResponseListener(this));
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        queue.add(request);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println("back button:: " + mActivity.getSearchresultsFragment());
                mActivity.setFragment(mActivity.getSearchresultsFragment());
                mActivity.setOpenprofileFragment(null);
                mActivity.setOpenprofileModel(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void validate(String tempPath) {

        mPicture = new ProfilePictureModel(mActivity.getBaseContext(), new File(tempPath));
        mUserModel.setTempProfilePicturePath(tempPath);

        try {
            mUserModel.updateJSON();
        } catch (JSONException e) {
            Toast.makeText(mActivity.getBaseContext(), "Could not update JSON", Toast.LENGTH_SHORT);
        }

        profilePicture_iv.setImageBitmap(mPicture.getImageBitmap());
        mActivity.setOpenprofileModel(mUserModel);
    }
}
