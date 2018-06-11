package app.chaffer.adapter;

/**
 * Created by Mac on 06/05/2018.
 */
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import app.chaffer.ChatMessage;
import app.chaffer.ChatActivity;
import app.chaffer.LoginActivity;
import app.chaffer.MainActivity;
import app.chaffer.R;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {

    private ChatActivity activity1;

    Fragment fragment;
    public MessageAdapter(MainActivity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref,ChatActivity activity1) {
        super(activity, modelClass, modelLayout, ref);
        this.activity1=activity1;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage.getMessageUser().equals(LoginActivity.userName))
            view =  activity1.getLayoutInflater().inflate(R.layout.item_out_message, viewGroup, false);
        else
            view = activity1.getLayoutInflater().inflate(R.layout.item_in_message, viewGroup, false);

        //generating view
        populateView(view, chatMessage, position);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }
}