package fragments;


import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.koobookandroidapp.R;

import controllers.BookController;
import dialogs.LikeBookDialog;


/**
 * A simple {@link Fragment} subclass.
 */
////Source- https://www.youtube.com/watch?v=zcnT-3F-9JA
public class BookReviewFragment extends Fragment implements RatingTabFragment.OnFragmentInteractionListener, BriefSummaryTabFragment.OnFragmentInteractionListener, ReviewsTabFragment.OnFragmentInteractionListener{

    TabLayout tabLayout;
    Toolbar toolbar;
    Button button_like;

    public BookReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BookController bookController = new BookController(getContext());
        toolbar = getActivity().findViewById(R.id.toolbar);
        button_like = getView().findViewById(R.id.button_like);
        bookController.displayBookInformation(view, getFragmentManager(),toolbar);
        tabLayout = getView().findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Brief"));
        tabLayout.addTab(tabLayout.newTab().setText("Rating"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = getView().findViewById(R.id.pager);
        final PagerAdapter adapter = new adapters.PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        button_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeBookDialog likeBookDialog = new LikeBookDialog();
                likeBookDialog.show(getFragmentManager(),"Like dialog");
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
