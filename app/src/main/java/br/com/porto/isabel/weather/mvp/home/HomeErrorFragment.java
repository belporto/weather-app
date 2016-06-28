package br.com.porto.isabel.weather.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.mvp.home.HomeFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeErrorFragment extends Fragment {

    @BindView(R.id.try_again)
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = configureView(inflater, container);
        return view;
    }

    private View configureView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.error_fragment, container, false);
        ButterKnife.bind(this, view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.container, new HomeFragment()).
                        addToBackStack(null).
                        commit();
            }
        });
        return view;
    }

}
