package com.example.project_riseup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupTextView = itemView.findViewById(R.id.groupTextView);
            joinButton = itemView.findViewById(R.id.joinButton);
        }

        public void bind(Group group, final OnJoinClickListener listener) {
            String date = group.getDate() != null ? dateFormat.format(group.getDate()) : "No birth date";
            String startTime = group.getStartTime() != null ? group.getStartTime() : "No contact time";
            String endTime = group.getEndTime() != null ? group.getEndTime() : "No contact time";

            groupTextView.setText("WorkOut: " + group.getWorkOut() + "\n" +
                    "Location: " + group.getLocation() + "\n" +
                    "Discribtion: " + group.getDiscribtion() + "\n" +
                    "Date: " + date + "\n" +
                    "Start time: " + startTime + "\n" +
                    "End time: " + endTime + "\n" +
                    "Limit Members Number: " + group.getMembersNumber() + "\n" +
                    "Joined Members Number: " + group.getHowManyJoin());

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onJoinClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
