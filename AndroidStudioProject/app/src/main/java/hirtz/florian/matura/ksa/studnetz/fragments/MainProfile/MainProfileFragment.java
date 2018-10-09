package hirtz.florian.matura.ksa.studnetz.fragments.MainProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.login.LoginActivity;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.activities.settings.SettingsoverviewActivity;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;

public class MainProfileFragment extends Fragment {

    private MainActivity mActivity;
    private UserModel mainprofileModel;

    public MainProfileFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
        mainprofileModel = mActivity.getMainprofileModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_main_profile, container, false);

        Toolbar toolbar = view.findViewById(R.id.mainprofile_toolbar);
        toolbar.setTitle(R.string.mainpage_title);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final TextView nameandfirstname_tv = view.findViewById(R.id.mainprofile_name_textview);
        nameandfirstname_tv.setText(mainprofileModel.getFirstname() + " " + mainprofileModel.getName());

        final TextView description_et = view.findViewById(R.id.mainprofile_description_textview);
        description_et.setText(mainprofileModel.getDescription());

        final TextView school_tv = view.findViewById(R.id.mainprofile_school_textview);
        school_tv.setText(mainprofileModel.getSchool() + mainprofileModel.getStringGrade());

        final ImageView profilepicture_iv = view.findViewById(R.id.mainprofile_profilepicture_imageview);
        profilepicture_iv.setImageBitmap(mActivity.getMainprofilePicture().getImageBitmap());

        //EMBLEMS::
        ImageView math_medal = view.findViewById(R.id.mainprofile_math_imageview);
        if (!mainprofileModel.isMaths()) {
            math_medal.setVisibility(View.GONE);
        }

        ImageView spanish_medal = view.findViewById(R.id.mainprofile_spanish_imageview);
        if (!mainprofileModel.isSpanish()) {
            spanish_medal.setVisibility(View.GONE);
        }

        ImageView physics_medal = view.findViewById(R.id.mainprofile_physics_imageview);
        if (!mainprofileModel.isPhysics()) {
            physics_medal.setVisibility(View.GONE);
        }

        ImageView german_medal = view.findViewById(R.id.mainprofile_german_imageview);
        if (!mainprofileModel.isGerman()) {
            german_medal.setVisibility(View.GONE);
        }

        ImageView biology_medal = view.findViewById(R.id.mainprofile_biology_imageview);
        if (!mainprofileModel.isBiology()) {
            biology_medal.setVisibility(View.GONE);
        }

        ImageView chemistry_medal = view.findViewById(R.id.mainprofile_chemistry_imageview);
        if (!mainprofileModel.isChemistry()) {
            chemistry_medal.setVisibility(View.GONE);
        }

        ImageView music_medal = view.findViewById(R.id.mainprofile_music_imageview);
        if (!mainprofileModel.isMusic()) {
            music_medal.setVisibility(View.GONE);
        }

        ImageView french_medal = view.findViewById(R.id.mainprofile_french_imageview);
        if (!mainprofileModel.isFrench()) {
            french_medal.setVisibility(View.GONE);
        }

        ImageView english_medal = view.findViewById(R.id.mainprofile_english_imageview);
        if (!mainprofileModel.isEnglish()) {
            english_medal.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainpage_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu_button:
                startSettingsActivity();
                return true;
            case R.id.logout_menu_button:
                System.out.println("::logout::");
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        Intent logout_intent = new Intent(mActivity, LoginActivity.class);
        startActivity(logout_intent);
    }

    private void startSettingsActivity() {
        Intent settings_intent = new Intent(mActivity, SettingsoverviewActivity.class);
        settings_intent.putExtra("clientInfo", mActivity.getExtras().getString("clientInfo"));
        startActivity(settings_intent);
    }
}