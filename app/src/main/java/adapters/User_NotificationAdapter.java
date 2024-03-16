package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_bandienthoai.R;

import java.util.List;

import models.User;
import models.User_Notification;

public class User_NotificationAdapter extends ArrayAdapter<User_Notification> {
    private Context mContext;
    private int mResource;
    public User_NotificationAdapter(@NonNull Context context, int resource,@NonNull List<User_Notification> objects) {

        super(context, resource,objects);
        mContext = context;
        mResource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(mResource, parent, false);
        }

        TextView textViewName = itemView.findViewById(R.id.title);
        TextView textViewTB = itemView.findViewById(R.id.textTB);
        TextView dateTB = itemView.findViewById(R.id.dateTB);
        User_Notification user_notification = getItem(position);

        if (user_notification != null) {
            textViewName.setText(user_notification.getName());
            textViewTB.setText(user_notification.getContent());
            dateTB.setText(String.valueOf(user_notification.getDate().getDay())+"/"+String.valueOf(user_notification.getDate().getMonth()));
            // textViewName.setText("user.getName()");
        }

        return itemView;
    }
}
