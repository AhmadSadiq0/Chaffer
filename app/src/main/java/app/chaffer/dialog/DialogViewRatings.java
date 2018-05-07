package app.chaffer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import app.chaffer.R;

/**
 * Created by Mac on 08/05/2018.
 */

public class DialogViewRatings extends Dialog {


    public DialogViewRatings(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_comment);
    }
}
