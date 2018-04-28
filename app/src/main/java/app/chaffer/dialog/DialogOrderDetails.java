package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.chaffer.MainActivity;
import app.chaffer.R;

/**
 * Created by Mac on 24/04/2018.
 */

public class DialogOrderDetails extends Dialog implements View.OnClickListener{
    TextView description ;
    TextView desDes ;
    TextView pickUpDes ;
    TextView pkgDes ;

    Button close ;


    public DialogOrderDetails(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_order_details);

        description=(TextView) findViewById(R.id.text_description) ;
        desDes=(TextView) findViewById(R.id.dropoff_up_location_description) ;
        pickUpDes=(TextView) findViewById(R.id.pick_up_location_description) ;
        pkgDes=(TextView) findViewById(R.id.package_description) ;

        close=(Button) findViewById(R.id.close) ;
        close.setOnClickListener(this);

        description.setText(MainActivity.selectedDeliveryOrderFromList.getDescription());
        desDes.setText(MainActivity.selectedDeliveryOrderFromList.getDesDes());
        pickUpDes.setText(MainActivity.selectedDeliveryOrderFromList.getPickUpDes());
        pkgDes.setText(MainActivity.selectedDeliveryOrderFromList.getPkgDes());



    }

    @Override
    public void onClick(View view) {
        if (view.getId()==close.getId()){
            dismiss();

        }

    }
}
