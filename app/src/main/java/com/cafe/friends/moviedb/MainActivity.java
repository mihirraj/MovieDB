package com.cafe.friends.moviedb;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Callback;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String GenreDetails = "GenreDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Bridge.get("https://api.themoviedb.org/3/genre/movie/list?api_key=676a0afb6adf02cdba938ed669e4198d&language=en-US")
                .throwIfNotSuccess() // optional
                .request(new Callback() {
                    @Override
                    public void response(Request request, Response response, BridgeException e) {
                        if (e != null) {
                            // See the 'Error Handling' section for information on how to process BridgeExceptions
                            int reason = e.reason();
                        } else {
                            // Use the Response object
                            String responseContent = response.asString();
                            //save response to shared preferences
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(GenreDetails, responseContent);
                            editor.apply();

                        }
                    }
                });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlaying(),getResources().getString(R.string.NowPlaying));
        adapter.addFragment(new UpcomingMovies(),getResources().getString(R.string.UpcomingMovies));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

