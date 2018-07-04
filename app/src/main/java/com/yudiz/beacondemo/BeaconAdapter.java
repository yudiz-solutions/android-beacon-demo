package com.yudiz.beacondemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estimote.coresdk.recognition.packets.Beacon;

import java.util.List;


class BeaconAdapter extends RecyclerView.Adapter<BeaconAdapter.ViewHolder> {

    private List<Beacon> beacons;

    BeaconAdapter(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_beacon_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvUUID.setText(beacons.get(position).getProximityUUID().toString().toUpperCase());
        holder.tvMajor.setText(String.valueOf(beacons.get(position).getMajor()));
        holder.tvMinor.setText(String.valueOf(beacons.get(position).getMinor()));
        holder.tvMac.setText(String.valueOf(beacons.get(position).getMacAddress()));
        holder.tvRSSI.setText(String.valueOf(beacons.get(position).getRssi()));
        holder.tvTX.setText(String.valueOf(beacons.get(position).getMeasuredPower()));
        holder.tvUniqueKey.setText(String.valueOf(beacons.get(position).getUniqueKey()));
    }

    void addItems(List<Beacon> beacons) {
        if (this.beacons != null) {
            this.beacons.clear();
            this.beacons.addAll(beacons);
            notifyDataSetChanged();
        }
    }

    void reset() {
        if (beacons != null)
            beacons.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return beacons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUUID;
        private TextView tvMinor;
        private TextView tvMajor;
        private TextView tvMac;
        private TextView tvRSSI;
        private TextView tvTX;
        private TextView tvUniqueKey;

        ViewHolder(View itemView) {
            super(itemView);

            tvUUID = itemView.findViewById(R.id.tv_uuid);
            tvMajor = itemView.findViewById(R.id.tv_major);
            tvMinor = itemView.findViewById(R.id.tv_minor);
            tvMac = itemView.findViewById(R.id.tv_mac);
            tvRSSI = itemView.findViewById(R.id.tv_rssi);
            tvTX = itemView.findViewById(R.id.tv_tx);
            tvUniqueKey = itemView.findViewById(R.id.tv_unique);
        }
    }
}
