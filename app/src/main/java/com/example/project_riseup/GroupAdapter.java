//package com.example.project_riseup;
//
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import android.graphics.Typeface;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.StyleSpan;
//
//public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
//
//    private final List<Group> groups = new ArrayList<>();
//    private final OnJoinClickListener onJoinClickListener;
//
//    public GroupAdapter(OnJoinClickListener onJoinClickListener) {
//        this.onJoinClickListener = onJoinClickListener;
//    }
//
//    @NonNull
//    @Override
//    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.group_item, parent, false);
//        return new GroupViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
//        Group group = groups.get(position);
//        holder.bind(group, onJoinClickListener);
//    }
//
//    @Override
//    public int getItemCount() {
//        return groups.size();
//    }
//
//    public void setGroups(List<Group> groups) {
//        this.groups.clear();
//        this.groups.addAll(groups);
//        notifyDataSetChanged();
//    }
//
//    public Group getGroupAtPosition(int position) {
//        return groups.get(position);
//    }
//
//    static class GroupViewHolder extends RecyclerView.ViewHolder {
//
//        private final TextView groupTextView;
//        private final Button joinButton;
//        private final ImageView groupPic;
//
//        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        public GroupViewHolder(@NonNull View itemView) {
//            super(itemView);
//            groupTextView = itemView.findViewById(R.id.groupTextView);
//            joinButton = itemView.findViewById(R.id.joinButton);
//            groupPic = itemView.findViewById(R.id.groupPic);
//        }
//
//        public void bind(Group group, final OnJoinClickListener listener) {
//            String date = group.getDate() != null ? dateFormat.format(group.getDate()) : "No birth date";
//            String startTime = group.getStartTime() != null ? group.getStartTime() : "No contact time";
//            String endTime = group.getEndTime() != null ? group.getEndTime() : "No contact time";
//
////            groupTextView.setText("Id: " + group.getId() + "\n" +
////                    "WorkOut: " + group.getWorkOut() + "\n" +
////                    "Location: " + group.getLocation() + "\n" +
////                    "Discribtion: " + group.getDiscribtion() + "\n" +
////                    "Date: " + date + "\n" +
////                    "Start time: " + startTime + "\n" +
////                    "End time: " + endTime + "\n" +
////                    "Limit Members Number: " + group.getMembersNumber() + "\n" +
////                    "Joined Members Number: " + group.getHowManyJoin());
//
//
//            // Create the full text
//            String fullText = "Id: " + group.getId() + "\n" +
//                    "Workout: " + group.getWorkOut() + "\n" +
//                    "Location: " + group.getLocation() + "\n" +
//                    "Discribtion: " + group.getDiscribtion() + "\n" +
//                    "Date: " + date + "\n" +
//                    "Start time: " + startTime + "\n" +
//                    "End time: " + endTime + "\n" +
//                    "Limit Members Number: " + group.getMembersNumber() + "\n" +
//                    "Joined Members Number: " + group.getHowManyJoin();
//
//            // Create a SpannableString
//            SpannableString spannableString = new SpannableString(fullText);
//
//            // Set the spans for bolding "Id:", "WorkOut:", and "Location:"
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Id:"), fullText.indexOf("Id:") + "Id:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("WorkOut:"), fullText.indexOf("WorkOut:") + "WorkOut:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Location:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Discribtion:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Date:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Start time:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("End time:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Limit Members Number:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), fullText.indexOf("Joined Members Number:"), fullText.indexOf("Location:") + "Location:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            // Set the styled text to the TextView
//            groupTextView.setText(spannableString);
//
//
//            groupPic.setImageResource(group.getImageGroup());
//
//            joinButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        listener.onJoinClick(getAdapterPosition());
//                    }
//                }
//            });
//        }
//    }
//}
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

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onJoinClick(getAdapterPosition());
                    }
                }
            });
        }

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
