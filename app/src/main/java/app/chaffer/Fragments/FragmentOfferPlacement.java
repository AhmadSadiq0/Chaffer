package app.chaffer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class FragmentOfferPlacement extends Fragment implements View.OnClickListener {

    private EditText txtAmount, txtTime, txtDescription;
    private Button btnPlaceOffer;
    private String url = LoginActivity.IP + "/users/offer";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_placement, container, false);

        txtDescription = (EditText) view.findViewById(R.id.editText_description);
        txtTime = (EditText) view.findViewById(R.id.editText_time);
        txtAmount = (EditText) view.findViewById(R.id.editText_amount);

        btnPlaceOffer = (Button) view.findViewById(R.id.place_offer);
        btnPlaceOffer.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnPlaceOffer.getId()) {

            if (!txtDescription.getText().toString().equals("") && !txtAmount.getText().toString().equals("")
                    && !txtTime.getText().toString().equals("")) {
                //putting data of request in a static array list defined in Main Activity

                //Seding request
                RequestQueue queue = Volley.newRequestQueue(getActivity());


                Map<String, String> postParam = new HashMap<String, String>();

                postParam.put("fkuser_id", MainActivity.selectedOfferFromRequestFeed.getUserId());
                postParam.put("fkrequest_id", MainActivity.selectedOfferFromRequestFeed.getRequestId());
                postParam.put("description", txtDescription.getText().toString());
                postParam.put("time_suggested", txtTime.getText().toString());
                postParam.put("amount", txtAmount.getText().toString());


//                For testing purpose only
                JSONObject jsonObject=new JSONObject(postParam) ;
                Log.d("obj",jsonObject.toString()) ;


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url
                        , new JSONObject(postParam),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("response", response.toString());

                                try {
                                    JSONObject object = new JSONObject(response.toString());
                                    if (object.getString("offer").equals("posted")) {

                                        Toast.makeText(getActivity(), "Request Posted", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }


                                //Hiding progress bar

                            }
                        }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error: " + error.getMessage());

                        Toast.makeText(getActivity(), "Error occurred!Please try again", Toast.LENGTH_LONG).show();


                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

                        //Hiding progress bar


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
        } else {
            Toast.makeText(getActivity(), "Kindly provide required descriptions", Toast.LENGTH_LONG).show();
        }

    }
}
