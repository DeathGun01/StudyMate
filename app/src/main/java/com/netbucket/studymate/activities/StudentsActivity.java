package com.netbucket.studymate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.netbucket.studymate.R;
import com.netbucket.studymate.adapters.StudentsPagerAdapter;

public class StudentsActivity extends AppCompatActivity {

    SimpleSearchView searchView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (searchView.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        MaterialToolbar materialToolbar = findViewById(R.id.toolbar_students);
        searchView = findViewById(R.id.searchView_students);
        searchView.setTabLayout(findViewById(R.id.tabLayout_students));
        searchView.closeSearch();
        setSupportActionBar(materialToolbar);

        materialToolbar.setNavigationOnClickListener(v -> onBackPressed());

        ViewPager2 viewPager = findViewById(R.id.viewPager_students);
        viewPager.setAdapter(new StudentsPagerAdapter(this));
        TabLayout tabLayout = findViewById(R.id.tabLayout_students);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.title_tab_students);
                    tab.setIcon(R.drawable.selector_item_members);
                    break;
                case 1:
                    tab.setText(R.string.title_tab_pending_requests);
                    tab.setIcon(R.drawable.selector_item_pending_requests);
                    BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                    badgeDrawable.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    badgeDrawable.setVisible(true);
                    badgeDrawable.setNumber(4);
                    badgeDrawable.setMaxCharacterCount(3);
                    break;

                case 2:
                    tab.setText(R.string.title_tab_suspended_students);
                    tab.setIcon(R.drawable.selector_item_suspended_members);
            }
        });

        tabLayoutMediator.attach();

        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_students, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            searchView.setMenuItem(item);
            return true;
        } else if (item.getItemId() == R.id.delete_all) {

        } else if (item.getItemId() == R.id.promote_all) {

        }
        return true;
    }
}