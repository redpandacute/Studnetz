package hirtz.florian.matura.ksa.studnetz.fragments.Searchresults;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.interfaces.LoaderFragment;
import hirtz.florian.matura.ksa.studnetz.util.ProfilePictureLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchresultsFragment extends Fragment implements LoaderFragment {

    private MainActivity mActivity;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private SearchResultsAdapter recAdapter;
    private static final int heapsize = 20;
    private boolean readyState;

    public SearchresultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searchresults, container, false);

        Toolbar toolbar = view.findViewById(R.id.searchresult_toolbar);
        toolbar.setTitle(R.string.searchresults_title);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = view.findViewById(R.id.searchresult_fragment_reclistview);

        layoutManager = new LinearLayoutManager(mActivity.getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recAdapter = new SearchResultsAdapter(mActivity.getSearchResultDataset(), this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.addOnScrollListener(new OnScrollResultsListener(layoutManager, this));

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mActivity.getSearchResultDataset().length >= heapsize) {
            new ProfilePictureLoader(this).load(0, heapsize, mActivity.getSearchResultDataset(), new OnProfilePicturesResponseListener(this));
        } else {
            new ProfilePictureLoader(this).load(0, mActivity.getSearchResultDataset().length, mActivity.getSearchResultDataset(), new OnProfilePicturesResponseListener(this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                mActivity.setFragment(mActivity.getSearchoverviewFragment());
                mActivity.setSearchResultDataset(null);
                mActivity.setSearchresultsFragment(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int getHeapsize() {
        return heapsize;
    }


    @Override
    public boolean getReadyState() {
        return this.readyState;
    }

    @Override
    public void setReadyState(boolean readyState) {
        this.readyState = readyState;
    }

    public LinearLayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    public void refreshList() {
        recAdapter.notifyDataSetChanged();
    }
}
