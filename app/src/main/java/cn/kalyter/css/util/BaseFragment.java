/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.css.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    protected abstract int getLayoutId();
}
