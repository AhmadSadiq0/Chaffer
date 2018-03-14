package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 07/03/2018.
 */


public class DialogPostedRequestDetailsFromOffer extends Dialog implements View.OnClickListener{

    TextView description ;
    TextView time ;
    TextView amount ;
    TextView pickUplocationDes ;
    TextView dropOffLocationDes ;
    TextView packageDescription ;

    Context context ;
    Button btn_close ;


    public DialogPostedRequestDetailsFromOffer(@NonNull Context context) {

        super(context);
        this.context=context ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        setContentView(R.layout.custom_dialog_posted_request_details);


        btn_close=(Button)findViewById(R.id.close) ;
        btn_close.setOnClickListener(this);



        description=(TextView) findViewById(R.id.text_description) ;
        amount=(TextView)findViewById(R.id.text_amount) ;
        time=(TextView)findViewById(R.id.text_time) ;
        pickUplocationDes=(TextView)findViewById(R.id.pick_up_location_description) ;
        dropOffLocationDes=(TextView)findViewById(R.id.dropoff_up_location_description) ;
        packageDescription=(TextView)findViewById(R.id.package_description) ;




        description.setText(""+MainActivity.selectedOfferFromOffersList.getRequest().getOfferDescription());
        time.setText(""+MainActivity.selectedOfferFromOffersList.getRequest().getTimeToDeliver());
        pickUplocationDes.setText(""+MainActivity.selectedOfferFromOffersList.getRequest().getPickUpLocationDescription());
        dropOffLocationDes.setText(""+MainActivity.selectedOfferFromOffersList.getRequest().getDrofOffLocationDescription());
        packageDescription.setText(""+MainActivity.selectedOfferFromOffersList.getRequest().getPackageDesription());
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==btn_close.getId()){
            dismiss();
        }
    }
}
