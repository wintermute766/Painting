package ru.sberbank.learning.painting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private PaintingView paintingView;
    private Button clear_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintingView = (PaintingView) findViewById(R.id.canvas) ;
        clear_button = (Button) findViewById(R.id.clear_button);

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintingView.clear();
            }
        });
    }
}
