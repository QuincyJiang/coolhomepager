package com.jiangxq.coolmenu;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jiangxq.coolmenuLib.CoolMenuFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt;
    ImageView closeMenu;
    boolean open;

    CoolMenuFrameLayout coolMenuFrameLayout;

    List<Fragment> fragments = new ArrayList<>();

    List<String> titleList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        coolMenuFrameLayout = (CoolMenuFrameLayout) findViewById(R.id.rl_main);
        coolMenuFrameLayout = $(R.id.rl_main);
        closeMenu = $(R.id.iv_close);
        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coolMenuFrameLayout.toggle();
            }
        });
        String[] titles = getResources().getStringArray(R.array.column);
        titleList = Arrays.asList(titles);
        coolMenuFrameLayout.setTitles(titleList);
//        coolMenuFrameLayout.setMenuIcon(R.drawable.menu2);

        fragments.add(new com.jiangxq.coolmenu.Fragment1());
        fragments.add(new com.jiangxq.coolmenu.Fragment2());
        fragments.add(new com.jiangxq.coolmenu.Fragment3());
        fragments.add(new com.jiangxq.coolmenu.Fragment4());

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        coolMenuFrameLayout.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }
}
