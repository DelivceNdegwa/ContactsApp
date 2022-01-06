package com.example.contactsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contactsapp.ContactDetailsActivity;
import com.example.contactsapp.R;
import com.example.contactsapp.models.ContactDetails;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    Context context;
    List<ContactDetails> contacts;
    public long contact_id;

    public ContactsAdapter(Context context, List<ContactDetails> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        ContactDetails contact = contacts.get(position);

        holder.txtContactName.setText(contact.getFirstName());
        Glide.with(context).load(contact.getProfileImage()).into(holder.imgContact);

        contact_id = holder.id;

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtContactName;
        ImageView imgContact;
        LinearLayout contactLayout;
        long id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtContactName = itemView.findViewById(R.id.txt_contact_name);
            imgContact = itemView.findViewById(R.id.img_contact);
            contactLayout = itemView.findViewById(R.id.contact_layout);

            contactLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ContactDetailsActivity.class);
                    intent.putExtra("CONTACT_ID", contact_id);
                    context.startActivity(intent);
                }
            });

        }
    }
}
