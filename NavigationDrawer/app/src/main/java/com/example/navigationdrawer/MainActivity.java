package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.navigationdrawer.ui.fragment.mainMenu.MainScreenFragment;
import com.example.navigationdrawer.ui.fragment.secondMenu.SecondMenuFragment;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

public class MainActivity extends AppCompatActivity {

    private Drawer.Result drawerResult = null;
    private Toolbar toolbar = null;
    
    private Fragment mainScreenFragment = null;
    private Fragment secondScreenFragment = null;
    
    private FrameLayout mainContainer = null;
    private FragmentTransaction fTrans = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // шукаємо всі View елементи на екрані
        findAllView();

        // ініціалізуємо toolbar
        initializeToolbar();

        // ініціалізуємо Navigation Drawer
        drawerResult = initializeDrawer();
        
        //
        createFragments();

        //
        setTitle("Home");

        //
        addFragment(mainScreenFragment);
    }
    
    private void addFragment(Fragment fragment) {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(mainContainer.getId(), fragment);
        fTrans.commit();
    }
    
    private void replaceFragment(Fragment fragment) {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(mainContainer.getId(), fragment);
        fTrans.commit();
    }
    
    
    private void createFragments() {
        mainScreenFragment = new MainScreenFragment();
        secondScreenFragment = new SecondMenuFragment();
    }

    private void findAllView() {
        toolbar = findViewById(R.id.toolbar);
        mainContainer = findViewById(R.id.main_container);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Drawer.Result initializeDrawer() {
        return new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обробка кліку
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        // якщо елемент меню має ім'я
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();

                            switch (MainActivity.this.getString(((Nameable) drawerItem).getNameRes())) {
                                case "Home": {
                                    setTitle("Home");
                                    replaceFragment(mainScreenFragment);
                                    break;
                                }
                                case "Free Play": {
                                    setTitle("Free Play");
                                    replaceFragment(secondScreenFragment);
                                    break;
                                }
                            }
                        }
                        // якщо елемент меню має значення
                        if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                // якщо значення елементу має в собі не інтове значення буде викликаний ексепшн який ми обробляємо
                                // в нашому випадку один з елементів має значення 12+ і символ "+" не може бути конвертований в int значення
                                try {
                                    // отримуємо інтове значення елементу меню
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        // якщо значення більше нуля обновлюємо значення елементу меню
                                        drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d("test", "Не нажимайте на бейдж який має знак + :)");
                                }
                            }
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // обробка довгого кліку, наприклад, тільки для SecondaryDrawerItem
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            // виводимо тост з іменем вибраного елементу меню
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        // Закриваємо Navigation Drawer по нажаттю системної кнопки "Назад" якщо він відкритий
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}