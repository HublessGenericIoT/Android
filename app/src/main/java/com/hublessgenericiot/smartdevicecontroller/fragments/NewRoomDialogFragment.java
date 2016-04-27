package com.hublessgenericiot.smartdevicecontroller.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.hublessgenericiot.smartdevicecontroller.R;

/**
 * Created by bwencke on 3/23/16.
 */
public class NewRoomDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NewRoomDialogListener {
//        public void onDialogPositiveClick(NewRoomDialogFragment dialog);
//        public void onDialogNegativeClick(NewRoomDialogFragment dialog);
        public void onNewRoomDialogResult(String roomName);
    }

    // Use this instance of the interface to deliver action events
    NewRoomDialogListener mListener;

    Dialog alertDialog;
    EditText roomName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Create new room")
                .setView(R.layout.fragment_new_room_dialog)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onNewRoomDialogResult(((EditText)alertDialog.findViewById(R.id.new_room_name)).getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onNewRoomDialogResult(null);
                       // mListener.onDialogNegativeClick(NewRoomDialogFragment.this);
                    }
                });

        alertDialog = builder.create();

        return alertDialog;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NewRoomDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NewRoomDialogListener");
        }
    }
}
