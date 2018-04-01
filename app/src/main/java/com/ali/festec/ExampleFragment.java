package com.ali.festec;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ali.latte.ec.launcher.BlankFragment;
import com.ali.latte.ec.launcher.DetailsTransition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 澄鱼 on 2018/3/17.
 */

public class ExampleFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_example, container,false);
        ButterKnife.bind(view);

        final AppCompatImageView  imagview = (AppCompatImageView)view.findViewById(R.id.xxx);
        imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment fragment = new BlankFragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragment.setSharedElementEnterTransition(new DetailsTransition());
                    setExitTransition(new Fade());
                    fragment.setEnterTransition(new Fade());
                    fragment.setSharedElementReturnTransition(new DetailsTransition());
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .addSharedElement(imagview, "fade")
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /*
    @Override
    public Object setLayout() {
        return R.layout.activity_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    //TODO: 测试方法， 没什么暖用
    private void testRestClient() {


        RestClient.builder()
                .url("https://www.cnblogs.com/whoislcj/p/5539239.html")
                .load(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.i("bbb", msg);
                    }
                })
                .build()
                .get();
       */

        /*
        RxRestClient.builder()
                .url("https://www.cnblogs.com/whoislcj/p/5539239.html")
                .load(getContext())
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

     */
}
