package com.hublessgenericiot.smartdevicecontroller;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hublessgenericiot.smartdevicecontroller.DeviceFragment.OnListFragmentInteractionListener;
import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessCallback;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.IHublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceType;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.ShadowState;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.ShadowedDevice;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Device} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyDeviceRecyclerViewAdapter extends RecyclerView.Adapter<MyDeviceRecyclerViewAdapter.ViewHolder> {

    private final List<Device> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDeviceRecyclerViewAdapter(List<Device> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem instanceof DeviceCreator) { //TODO have a field for this or get rid of it
            holder.mNewDeviceView.setVisibility(View.VISIBLE);
        } else {
            holder.mNewDeviceView.setVisibility(View.GONE);
        }
        holder.mNameView.setText(mValues.get(position).getName());
        Log.d("Outside", mValues.get(position).getName());
        Log.d("Outside", new Boolean(holder.mItem instanceof ShadowedDevice).toString());
        Log.d("Outside2", new Boolean(((ShadowedDevice) holder.mItem).getShadow() != null).toString());
        Log.d("Outside3", new Boolean(((ShadowedDevice) holder.mItem).getShadow().getState() != null).toString());
        if(holder.mItem instanceof ShadowedDevice && ((ShadowedDevice) holder.mItem).getShadow() != null && ((ShadowedDevice) holder.mItem).getShadow().getState() != null) {
            ShadowState state = ((ShadowedDevice) holder.mItem).getShadow().getState();
            if(state.getDesired().containsKey("state")) { // TODO: Saved this String elsewhere
                holder.mStateView.setChecked(state.getDesired().get("state").equals("on"));
                Log.d("Set", Boolean.valueOf(state.getDesired().get("state").equals("on")).toString());
            } else if(state.getReported().containsKey("state")) {
                holder.mStateView.setChecked(state.getDesired().get("state").equals("on"));
            } else {
                holder.mStateView.setChecked(false);
            }
        } else {
            holder.mStateView.setChecked(false);
            holder.mStateView.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mStateView.performClick();
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onDeviceClick(holder.mItem);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onDeviceLongClick(holder.mItem);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNewDeviceView;
        public final TextView mNameView;
        public final Switch mStateView;
        public Device mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNewDeviceView = (TextView) view.findViewById(R.id.newDevice);
            mNameView = (TextView) view.findViewById(R.id.name);
            mStateView = (Switch) view.findViewById(R.id.state);

            mStateView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(mItem instanceof ShadowedDevice && ((ShadowedDevice) mItem).getShadow() != null && ((ShadowedDevice) mItem).getShadow().getState() != null) {
                        Map<String, String> desired = ((ShadowedDevice) mItem).getShadow().getState().getDesired();
                        desired.put("state", isChecked ? "on" : "off"); // TODO: Save this String elsewhere
                        ((ShadowedDevice) mItem).getShadow().getState().setDesired(desired);

                        /*DeviceCreator dc = new DeviceCreator(mItem);
                        IHublessSdkService instance = HublessSdkService.getInstance(getActivity());
                        instance.updateDevice(mItem.getId(), dc).enqueue(new HublessCallback<DeviceUpdatedResponse>() {
                            @Override
                            public void doOnResponse(Call<DeviceUpdatedResponse> call, retrofit2.Response<DeviceUpdatedResponse> response) {
                                Toast.makeText(getActivity().getApplicationContext(), "Device Updated", Toast.LENGTH_LONG).show();
                                //TODO this is not an accepatble long-term solution, must refresh tab
                            }
                        });*/

                        if (SavedDeviceList.mqttService != null && SavedDeviceList.mqttService.isConnected()) {
                            Gson gson = new Gson();
                            String json = gson.toJson(((ShadowedDevice) mItem).getShadow());
                            SavedDeviceList.mqttService.publish("proxy/$aws/things/" + mItem.getId() + "/shadow/update/desired",
                                    json, getActivity());
                        }
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStateView.getText() + "'";
        }

        private Activity getActivity() {
            Context context = mView.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity)context;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            return null;
        }
    }
}
