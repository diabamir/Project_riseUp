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
                    "Discribtion: " + group.getDiscribtion() + "\n" +
                    "Date: " + date + "\n" +
                    "Start time: " + startTime + "\n" +
                    "End time: " + endTime + "\n" +
                    "Limit Members Number: " + group.getMembersNumber() + "\n" +
                    "Joined Members Number: " + group.getHowManyJoin();

            // Create a SpannableString
            SpannableString spannableString = new SpannableString(fullText);

            // Apply bold style to the specified labels
            applyBoldSpan(spannableString, fullText, "Id:");
            applyBoldSpan(spannableString, fullText, "Workout:");
            applyBoldSpan(spannableString, fullText, "Location:");
            applyBoldSpan(spannableString, fullText, "Discribtion:");
            applyBoldSpan(spannableString, fullText, "Date:");
            applyBoldSpan(spannableString, fullText, "Start time:");
            applyBoldSpan(spannableString, fullText, "End time:");
            applyBoldSpan(spannableString, fullText, "Limit Members Number:");
            applyBoldSpan(spannableString, fullText, "Joined Members Number:");

            // Set the styled text to the TextView
            groupTextView.setText(spannableString);

            groupPic.setImageResource(group.getImageGroup());

            long currentUserId = 1;
            long groupId = group.getId();
            UserGroupApi api = ApiClient.getClient().create(UserGroupApi.class);
            // Make the API call to get the user's status in the group
            Call<UserGroupJoin> call = api.getUserStatusInGroup(currentUserId, group.getId());
            call.enqueue(new Callback<UserGroupJoin>() {
                @Override
                public void onResponse(Call<UserGroupJoin> call, Response<UserGroupJoin> response) {
                    if (response.isSuccessful()) {
                        UserGroupJoin userGroupJoin = response.body();
                        if (userGroupJoin != null) {
                            String status = userGroupJoin.getStatus();
                            // Update the button text based on the status
                            joinButton.setText("requested".equals(status) ? "Requested" : "Join");
                        }
                    } else {
                        // Handle the case where the response was not successful
                        // (e.g., show an error message or log the issue)
                        joinButton.setText("Join"); // Default to "Join" in case of error
                    }
                }

                @Override
                public void onFailure(Call<UserGroupJoin> call, Throwable t) {
                    // Handle the error
                    // (e.g., show an error message or log the issue)
                    joinButton.setText("Join"); // Default to "Join" in case of failure
                }
            });
        }

//            joinButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        listener.onJoinClick(getAdapterPosition());
//                    }
//                    UserGroupApi api = ApiClient.getClient().create(UserGroupApi.class);
//                    long currentUserId=1;
//                    Call<UserGroupJoin> call = api.getUserStatusInGroup(currentUserId, group.getId());
//
//
//
//                }
//            });
//        }

        // Helper method to apply bold style safely
        private void applyBoldSpan(SpannableString spannableString, String fullText, String target) {
            int start = fullText.indexOf(target);
            if (start != -1) { // Check if the target substring is found
                int end = start + target.length();
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}