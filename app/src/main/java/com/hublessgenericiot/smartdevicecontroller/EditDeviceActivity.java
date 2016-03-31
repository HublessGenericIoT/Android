package com.hublessgenericiot.smartdevicecontroller;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;


public class EditDeviceActivity extends AppCompatActivity implements ItemDataHolder, NewRoomDialogFragment.NewRoomDialogListener, AutomationDialogFragment.AutomationDialogListener {

    public static final String DEVICE_ID = "device_id";
    public static final int DEVICE_EDITED = 0;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra(DEVICE_ID);

        setContentView(R.layout.activity_edit_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create new fragment and transaction
        Fragment newFragment = new EditDeviceActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();    //Call the back button's method
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getDeviceId() {
        return id;
    }

    public void finishWithResult(boolean modified, boolean newRoom) {
        Bundle conData = new Bundle();
        conData.putBoolean("modified", modified);
        conData.putBoolean("newRoom", newRoom);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void showNewRoomDialog() {
        DialogFragment dialog = new NewRoomDialogFragment();
        dialog.show(getFragmentManager(), "NewRoomDialogFragment");
    }

    public void showAutomationDialog() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        AutomationDialogFragment newFragment = new AutomationDialogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.fragment, newFragment)
                .addToBackStack(null).commit();

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        this.getSupportActionBar().setTitle("Add Automation");
    }

    @Override
    public void onAutomationDialogResult() {
        Toast.makeText(this, "Automation dialog result", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewRoomDialogResult(String roomName) {
        for(Fragment fragment : getSupportFragmentManager().getFragments()) {
            if(fragment instanceof EditDeviceActivityFragment) {
                ((EditDeviceActivityFragment)fragment).returnNewRoom(roomName);
            }
        }
    }

    public void revertMenu() {
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        this.getSupportActionBar().setTitle("Edit Device");
    }
}
