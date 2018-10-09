package hirtz.florian.matura.ksa.studnetz.activities.main;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.login.LoginActivity;
import hirtz.florian.matura.ksa.studnetz.fragments.Chat.OpenChatFragment;
import hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview.ChatoverviewFragment;
import hirtz.florian.matura.ksa.studnetz.fragments.Chatoverview.OpenChatModel;
import hirtz.florian.matura.ksa.studnetz.fragments.MainProfile.MainProfileFragment;
import hirtz.florian.matura.ksa.studnetz.fragments.Searchoverview.SearchoverviewFragment;
import hirtz.florian.matura.ksa.studnetz.fragments.Searchresults.SearchresultsFragment;
import hirtz.florian.matura.ksa.studnetz.fragments.UserProfile.OpenProfileFragment;
import hirtz.florian.matura.ksa.studnetz.models.ChatModel;
import hirtz.florian.matura.ksa.studnetz.models.ProfilePictureModel;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;
import hirtz.florian.matura.ksa.studnetz.util.JSONtoInfo;

public class MainActivity extends AppCompatActivity {

    private SearchoverviewFragment searchoverviewFragment;
    private MainProfileFragment mainprofileFragment;
    private ChatoverviewFragment chatoverviewFragment;
    private SearchresultsFragment searchresultsFragment;
    private OpenProfileFragment openprofileFragment;
    private OpenChatFragment openchatFragment;

    private Map<Integer, String> openChatsPaths;
    private Map<Integer, OpenChatModel> openChatsDataset;

    private Bundle extras;

    private UserModel mainprofileModel, openprofileModel;
    private ChatModel openChat;
    private UserModel[] searchResultDataset;

    private DatabaseReference mainprofileFirebaseRef;
    private DatabaseReference chatoverviewDBReference;

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.searchoverview_botnav:
                    if(openprofileFragment != null) {
                        setFragment(openprofileFragment);
                        return true;
                    }
                    if(searchresultsFragment != null) {
                        setFragment(searchresultsFragment);
                        return true;
                    }
                    setFragment(searchoverviewFragment);
                    return true;

                case R.id.chatoverview_botnav:
                    if(openchatFragment != null) {
                        setFragment(openchatFragment);
                        return true;
                    }
                    setFragment(chatoverviewFragment);
                    return true;

                case R.id.mainprofile_botnav:
                    setFragment(mainprofileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //initialize all needed Objects
        extras = getIntent().getExtras();
        mainprofileModel = null;
        openprofileModel = null;

        openChat = null;

        openChatsPaths = new HashMap<>();
        openChatsDataset = new LinkedHashMap<>();


        try {
            mainprofileModel = new JSONtoInfo(getBaseContext()).createNewItem(new JSONObject(extras.getString("clientInfo")));
        } catch (JSONException e) {
            e.printStackTrace();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }

        mainprofileFirebaseRef = FirebaseDatabase.getInstance().getReference().child(String.format("Users/%d", mainprofileModel.getId()));
        ProfilePictureModel mainprofilePicture = new ProfilePictureModel(getBaseContext(), new File(mainprofileModel.getTempProfilePicturePath()));

        //Initialize all fragments
        mainprofileFragment = new MainProfileFragment();
        searchoverviewFragment = new SearchoverviewFragment();
        chatoverviewFragment = new ChatoverviewFragment();

        searchresultsFragment = null;
        openprofileFragment = null;
        openchatFragment = null;

        setFragment(mainprofileFragment);
        navigation.setSelectedItemId(R.id.mainprofile_botnav);
    }

    public void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void changeFragmentwithBackstack(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public void popBackstack(String tag) {
        getFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void refreshDataset(int start, int amount, String[] paths) {
        for (int i = 0; i < amount; i++) {
            searchResultDataset[i + start].setTempProfilePicturePath(paths[i]);
        }
    }

    public SearchoverviewFragment getSearchoverviewFragment() {
        return searchoverviewFragment;
    }

    public void setSerchoverviewFragment(SearchoverviewFragment searchoverviewFragment) {
        this.searchoverviewFragment = searchoverviewFragment;
    }

    public void setSearchoverviewFragment(SearchoverviewFragment searchoverviewFragment) {
        this.searchoverviewFragment = searchoverviewFragment;
    }

    public MainProfileFragment getMainprofileFragment() {
        return mainprofileFragment;
    }

    public void setMainprofileFragment(MainProfileFragment mainprofileFragment) {
        this.mainprofileFragment = mainprofileFragment;
    }

    public ChatoverviewFragment getChatoverviewFragment() {
        return chatoverviewFragment;
    }

    public void setChatoverviewFragment(ChatoverviewFragment chatoverviewFragment) {
        this.chatoverviewFragment = chatoverviewFragment;
    }

    public SearchresultsFragment getSearchresultsFragment() {
        return searchresultsFragment;
    }

    public void setSearchresultsFragment(SearchresultsFragment searchresultsFragment) {
        this.searchresultsFragment = searchresultsFragment;
    }

    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public UserModel getMainprofileModel() {
        return mainprofileModel;
    }

    public void setMainprofileModel(UserModel mainprofileModel) {
        this.mainprofileModel = mainprofileModel;
    }

    public UserModel getOpenprofileModel() {
        return openprofileModel;
    }

    public void setOpenprofileModel(UserModel openprofileModel) {
        this.openprofileModel = openprofileModel;
    }

    public ProfilePictureModel getMainprofilePicture() {
        return new ProfilePictureModel(getBaseContext(), new File(mainprofileModel.getTempProfilePicturePath()));
    }

    public ProfilePictureModel getOpenprofilePicture() {
        return new ProfilePictureModel(getBaseContext(), new File(openprofileModel.getTempProfilePicturePath()));
    }

    public DatabaseReference getMainProfileFirebaseRef() {
        return mainprofileFirebaseRef;
    }

    public void setMainprofileFirebaseRef(DatabaseReference chatoverviewDBReference) {
        this.chatoverviewDBReference = chatoverviewDBReference;
    }

    public UserModel[] getSearchResultDataset() {
        return this.searchResultDataset;
    }

    public void setSearchResultDataset(UserModel[] searchResultDataset) {
        this.searchResultDataset = searchResultDataset;
    }

    public OpenProfileFragment getOpenprofileFragment() {
        return this.openprofileFragment;
    }

    public void setOpenprofileFragment(OpenProfileFragment openprofileFragment) {
        this.openprofileFragment = openprofileFragment;
    }

    public ChatModel getOpenChat() {
        return openChat;
    }

    public void setOpenChat(ChatModel openChat) {
        this.openChat = openChat;
    }

    public OpenChatFragment getOpenchatFragment() {
        return openchatFragment;
    }

    public void setOpenchatFragment(OpenChatFragment openchatFragment) {
        this.openchatFragment = openchatFragment;
    }

    public BottomNavigationView getNavigation() {
        return navigation;
    }

    public Map<Integer, String> getOpenChatsPaths() {
        return openChatsPaths;
    }

    public void setOpenChatsPaths(Map<Integer, String> openChatsPaths) {
        this.openChatsPaths = openChatsPaths;
    }

    public Map<Integer, OpenChatModel> getOpenChatsDataset() {
        return openChatsDataset;
    }

    public void setOpenChatsDataset(Map<Integer, OpenChatModel> openChatsDataset) {
        this.openChatsDataset = openChatsDataset;
    }
}
