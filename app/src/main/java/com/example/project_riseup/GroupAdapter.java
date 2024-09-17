package com.example.project_riseup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private final List<Group> groups = new ArrayList<>();
    private final OnJoinClickListener onJoinClickListener;

    public GroupAdapter(OnJoinClickListener onJoinClickListener) {
        this.onJoinClickListener = onJoinClickListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.bind(group, onJoinClickListener);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void setGroups(List<Group> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        notifyDataSetChanged();
    }

    public Group getGroupAtPosition(int position) {
        return groups.get(position);
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {

        private final TextView groupTextView;
        private final Button joinButton;
        private final ImageView groupPic;

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupTextView = itemView.findViewById(R.id.groupTextView);
            joinButton = itemView.findViewById(R.id.joinButton);
            groupPic = itemView.findViewById(R.id.groupPic);
        }

        public void bind(Group group, final OnJoinClickListener listener) {
            String date = group.getDate() != null ? dateFormat.format(group.getDate()) : "No birth date";
            String startTime = group.getStartTime() != null ? group.getStartTime() : "No contact time";
            String endTime = group.getEndTime() != null ? group.getEndTime() : "No contact time";

            // Create the full text
            String fullText = "Id: " + group.getId() + "\n" +
                    "Workout: " + group.getWorkOut() + "\n" +
                    "Location: " + group.getLocation() + "\n" +
                    "Description: " + group.getDiscribtion() + "\n" +
                    "Date: " + date + "\n" +
                    "Start time: " + startTime + "\n" +
                    "End time: " + endTime + "\n" +
                    "Limit Members Number: " + group.getMembersNumber() + "\n" +
                    "Joined Members Number: " + group.getHowManyJoin()


                    ;

            // Create a SpannableString
            SpannableString spannableString = new SpannableString(fullText);

            // Set the spans for bolding each label
            setBoldSpan(spannableString, "Id:");
            setBoldSpan(spannableString, "Workout:");
            setBoldSpan(spannableString, "Location:");
            setBoldSpan(spannableString, "Description:");
            setBoldSpan(spannableString, "Date:");
            setBoldSpan(spannableString, "Start time:");
            setBoldSpan(spannableString, "End time:");
            setBoldSpan(spannableString, "Limit Members Number:");
            setBoldSpan(spannableString, "Joined Members Number:");

            // Set the styled text to the TextView
            groupTextView.setText(spannableString);

            groupPic.setImageResource(group.getImageGroup());

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onJoinClick(getAdapterPosition());

                        // Change the button text to "Joined"
                        joinButton.setText("Joined");
                        joinButton.setEnabled(false); // Disable the button to prevent further clicks
                    }
                }
            });
        }

        // Helper method to set bold span
        private void setBoldSpan(SpannableString spannableString, String label) {
            int start = spannableString.toString().indexOf(label);
            if (start >= 0) {
                int end = start + label.length();
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

    }
}