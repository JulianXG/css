package cn.kalyter.css.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import java.util.List;

import cn.kalyter.css.R;
import cn.kalyter.css.contract.MainContract;
import cn.kalyter.css.data.source.UserSource;
import cn.kalyter.css.model.Role;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.view.BoardFragment;
import cn.kalyter.css.view.MeFragment;
import cn.kalyter.css.view.NewRepairFragment;
import cn.kalyter.css.view.PaymentFragment;
import cn.kalyter.css.view.PaymentPropertyFragment;
import cn.kalyter.css.view.RepairFragment;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class MainPresenter implements MainContract.Presenter,
        BottomNavigationView.OnNavigationItemSelectedListener{

    private MainContract.View mView;
    private FragmentManager mFragmentManager;
    private UserSource mUserSource;
    private Context mContext;
    private Role mRole;

    public MainPresenter(MainContract.View view,
                         FragmentManager fragmentManager,
                         Context context,
                         UserSource userSource) {
        mView = view;
        mFragmentManager = fragmentManager;
        mContext = context;
        mUserSource = userSource;
    }

    @Override
    public void start() {
        mView.setBottomItemSelectListener(this);
        mView.showDefaultSection();
        mRole = mUserSource.getRole();
    }

    @Override
    public void toggleFragment(int resId) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentTransaction.hide(fragment);
            }
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(String.valueOf(resId));
        if (fragment != null) {
            fragmentTransaction.show(fragment);
        } else {
            switch (resId) {
                case R.id.board:
                    fragmentTransaction.add(
                            R.id.frame_content, new BoardFragment(),
                            String.valueOf(resId));
                    break;
                case R.id.payment:
                    if (mRole.getId() == Config.ROLE_OWNER) {
                        fragmentTransaction.add(
                                R.id.frame_content, new PaymentFragment(),
                                String.valueOf(resId));
                    } else if (mRole.getId() == Config.ROLE_PROPERTY) {
                        fragmentTransaction.add(R.id.frame_content, new PaymentPropertyFragment(),
                                String.valueOf(resId));
                    }
                    break;
                case R.id.repair:
                    if (mRole.getId() == Config.ROLE_OWNER) {
                        fragmentTransaction.add(R.id.frame_content,
                                new NewRepairFragment(),
                                String.valueOf(resId));
                    } else if (mRole.getId() == Config.ROLE_PROPERTY) {
                        fragmentTransaction.add(
                                R.id.frame_content, new RepairFragment(),
                                String.valueOf(resId));
                    }
                    break;
                case R.id.me:
                    fragmentTransaction.add(
                            R.id.frame_content, new MeFragment(),
                            String.valueOf(resId));
                    break;
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void loadBadge() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toggleFragment(item.getItemId());
        return true;
    }
}
