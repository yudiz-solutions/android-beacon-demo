package com.yudiz.beacondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button btnScan;
    private Button btnReset;
    private RecyclerView rvBeaconList;
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private BeaconAdapter beaconAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initTasks();
        listeners();
    }


    private void listeners() {
        btnScan.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    private void initTasks() {

        beaconManager = ((MyApp) getApplicationContext()).beaconManager;
        beaconRegion = new BeaconRegion("monitored region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                null, null);
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
                beaconAdapter.addItems(beacons);
                btnReset.setEnabled(true);

                Log.d(TAG, "onEnteredRegion: ");
            }

            @Override
            public void onExitedRegion(BeaconRegion region) {
                Toast.makeText(MainActivity.this, "onExitedRegion", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onExitedRegion: " + region);
            }
        });
        beaconAdapter = new BeaconAdapter(new ArrayList<Beacon>());
        rvBeaconList.setLayoutManager(new LinearLayoutManager(this));
        rvBeaconList.setAdapter(beaconAdapter);
    }

    private void initViews() {
        btnScan = findViewById(R.id.btnScan);
        btnReset = findViewById(R.id.btnReset);
        rvBeaconList = findViewById(R.id.rvBeaconList);
        progressBar = findViewById(R.id.pb);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onDestroy() {
        if (beaconManager != null)
            beaconManager.disconnect();
        beaconManager = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnScan:
                if (btnScan.getText().toString().equals(getString(R.string.scan_beacon))) {
                    progressBar.setVisibility(View.VISIBLE);
                    beaconManager.startMonitoring(beaconRegion);
                    btnScan.setText(getString(R.string.stop_beacon));
                } else if (btnScan.getText().toString().equals(getString(R.string.stop_beacon))) {
                    progressBar.setVisibility(View.GONE);
                    beaconManager.stopMonitoring(beaconRegion.getIdentifier());
                    btnScan.setText(getString(R.string.scan_beacon));
                }
                break;
            case R.id.btnReset:
                beaconAdapter.reset();
                btnReset.setEnabled(false);
                break;

        }
    }
}
