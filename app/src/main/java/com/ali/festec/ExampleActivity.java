package com.ali.festec;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Fade;
import android.view.View;

import com.ali.latte.ec.launcher.DetailsTransition;

/**
 * Created by 澄鱼 on 2018/4/15.
 */

public class ExampleActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        final FloatingActionButton button = (FloatingActionButton)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent intent = new Intent(ExampleActivity.this, ExampleActivity2.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(ExampleActivity.this, view, "float");
                // start the new activity
                startActivity(intent, options.toBundle());

            }
        });
    }
}
