package com.hublessgenericiot.smartdevicecontroller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hublessgenericiot.smartdevicecontroller.DeviceFragment.OnListFragmentInteractionListener;
import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent.DummyItem;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.models.ShadowedDevice;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDeviceRecyclerViewAdapter extends RecyclerView.Adapter<MyDeviceRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private final List<ShadowedDevice> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDeviceRecyclerViewAdapter(List<ShadowedDevice> items, OnListFragmentInteractionListener listener) {
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
        if(mValues.get(position).getDevice() != null) {
            holder.mNewDeviceView.setVisibility(View.VISIBLE);
        } else {
            holder.mNewDeviceView.setVisibility(View.GONE);
        }
        holder.mNameView.setText(mValues.get(position).getDevice().getThingName());
        holder.mStateView.setChecked(true); //mValues.get(position).getDevice().getDefaultClientId());

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
        public ShadowedDevice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNewDeviceView = (TextView) view.findViewById(R.id.newDevice);
            mNameView = (TextView) view.findViewById(R.id.name);
            mStateView = (Switch) view.findViewById(R.id.state);

            mStateView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //mItem.state = isChecked;
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStateView.getText() + "'";
        }
    }
}
