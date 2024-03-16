package adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_bandienthoai.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import models.User;
import models.Voucher;

public class VoucherAdapter extends ArrayAdapter<Voucher> {
    private Context mContext;
    DatabaseReference usersRef;
   String myID;
    private int mResource;
    public VoucherAdapter(@NonNull Context context, int resource, @NonNull List<Voucher> objects,String myId) {
        super(context, resource, objects);
        mContext = context;
        mResource=resource;
        myID = myId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(myID).child("vouchers");

        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(mResource, parent, false);
        }

        TextView textViewName = itemView.findViewById(R.id.title1);
        Button b=itemView.findViewById(R.id.btLuu1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thêm voucher thanh công", Toast.LENGTH_SHORT).show();
                Voucher voucher= getItem(position);
                usersRef.child(voucher.getId()).setValue(voucher);


            }
        });


        Voucher voucher= getItem(position);

        if (voucher != null) {
            textViewName.setText(voucher.getDescription());

            // textViewName.setText("user.getName()");
        }

        return itemView;
    }
}
