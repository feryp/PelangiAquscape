package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FormPembelianActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pembelian);

        cancel =(ImageView) findViewById(R.id.im_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onRadioButtonClick(View view) {
        RadioGroup radioGroup = findViewById(R.id.radio_metode_pembayaran);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(this, "Pilih " + radioButton.getText() , Toast.LENGTH_SHORT).show();
    }
}
