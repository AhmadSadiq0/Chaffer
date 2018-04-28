package app.chaffer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import static app.chaffer.LoginActivity.token;

/**
 * Created by Mac on 16/02/2018.
 */

public class SignUpUserForm extends Activity implements View.OnClickListener {

    EditText firstNameText;
    EditText lastNameText;
    EditText textCnic;
    EditText textPhoneNumber;
    ProgressBar progressBar;
    Button submit;
    String gender;
    RadioButton male;
    RadioButton female;
    private String url = LoginActivity.IP + "/users/inforeg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);


        firstNameText = (EditText) findViewById(R.id.text_first_name);
        lastNameText = (EditText) findViewById(R.id.text_last_name);
        ;
        textCnic = (EditText) findViewById(R.id.text_cnic);
        ;
        textPhoneNumber = (EditText) findViewById(R.id.text_phoneNumber);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        submit = (Button) findViewById(R.id.submint_signup_form);
        submit.setOnClickListener(this);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);


        if (female.isChecked()) {
            gender = "F";

        } else {
            gender = "M";
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == submit.getId()) {
            int isValid = validateData();
            if(isValid == 0){
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            RequestQueue queue = Volley.newRequestQueue(this);


            Map<String, String> postParam = new HashMap<String, String>();
            //postParam.put("fkuserid", "5");
            postParam.put("first_name", firstNameText.getText().toString());
            postParam.put("last_name", lastNameText.getText().toString());
            postParam.put("phone", textPhoneNumber.getText().toString());
            postParam.put("cnic", textCnic.getText().toString());
            postParam.put("gender", gender);


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url
                    , new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", response.toString());

                            try {
                                JSONObject object = new JSONObject(response.toString());
                                if (object.getString("user").equals("updated")) {

                                    Toast.makeText(getApplicationContext(), "User updated", Toast.LENGTH_LONG).show();


                                    //Starting main activity
                                    Intent intent = new Intent(SignUpUserForm.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                            } catch (Exception e) {
                                // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                            }


                            //Hiding progress bar
                            progressBar.setVisibility(View.GONE);

                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error: " + error.getMessage());

                    Toast.makeText(getApplicationContext(), "Error occured!Please try again", Toast.LENGTH_LONG).show();


                    //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

                    //Hiding progress bar
                    progressBar.setVisibility(View.GONE);


                }
            }) {

                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //  params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", token);
                    return params;
                }


            };

            jsonObjReq.setTag("json");
            // Adding request to request queue
            queue.add(jsonObjReq);

            }

        }


    public int validateData() {
        if(firstNameText.getText().toString().equals("")){
            Toast.makeText(this,"Error: Enter First name.",Toast.LENGTH_SHORT).show();
            firstNameText.setFocusable(true);
            return 0;
        }
        if(lastNameText.getText().toString().equals("")){
            Toast.makeText(this,"Error: Enter Last name.",Toast.LENGTH_SHORT).show();
            lastNameText.setFocusable(true);
            return 0;
        }
        if(textCnic.getText().toString().equals("")){
            Toast.makeText(this,"Error: Enter CNIC.",Toast.LENGTH_SHORT).show();
            textCnic.setFocusable(true);
            return 0;
        }else if(!TextUtils.isDigitsOnly(textCnic.getText().toString()) || textCnic.getText().toString().length() != 13){
            Toast.makeText(this,"Error: Invalid CNIC. CNIC should be 13 digits.",Toast.LENGTH_SHORT).show();
            textCnic.setFocusable(true);
            return 0;
        }
        if(textPhoneNumber.getText().toString().equals("")){
            Toast.makeText(this,"Error: Enter Phone number",Toast.LENGTH_SHORT).show();
            textPhoneNumber.setFocusable(true);
            return 0;
        }else if(!TextUtils.isDigitsOnly(textPhoneNumber.getText().toString()) || textPhoneNumber.getText().toString().length() != 10) {
            Toast.makeText(this,"Error: Invalid Phone number. Phone number should be 10 digits.",Toast.LENGTH_SHORT).show();
            textPhoneNumber.setFocusable(true);
            return 0;
        }
        if(gender.equals("") || gender == null){
            Toast.makeText(this,"Error: Select Gender",Toast.LENGTH_SHORT).show();
            return 0;
        }

        return 1;
    }
}
