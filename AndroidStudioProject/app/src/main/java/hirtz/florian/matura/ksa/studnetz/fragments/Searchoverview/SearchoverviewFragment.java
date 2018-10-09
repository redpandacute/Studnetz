package hirtz.florian.matura.ksa.studnetz.fragments.Searchoverview;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.activities.main.MainActivity;
import hirtz.florian.matura.ksa.studnetz.util.SchoolMapper;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchoverviewFragment extends Fragment {

    private MainActivity mActivity;

    public SearchoverviewFragment() {
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

        View view = inflater.inflate(R.layout.fragment_searchoverview, container, false);

        Toolbar toolbar = view.findViewById(R.id.searchoverview_toolbar);
        toolbar.setTitle(R.string.search_title);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ImageButton searchbutton = view.findViewById(R.id.searchoverview_searchbutton_imagebutton);

        Spinner schoolSpinner = view.findViewById(R.id.searchoverview_filterbyschool_spinner);
        Spinner gradeSpinner = view.findViewById(R.id.searchoverview_grade_spinner);

        new SchoolMapper(mActivity.getBaseContext(), schoolSpinner, gradeSpinner).startDisplay("schoollist.txt");

        searchbutton.setOnClickListener(new OnSearchListener(view, this));

        setHasOptionsMenu(true);

        return view;
    }
}
