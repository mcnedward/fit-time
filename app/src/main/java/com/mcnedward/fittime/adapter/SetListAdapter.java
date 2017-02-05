package com.mcnedward.fittime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.Set;
import com.mcnedward.fittime.utils.Extension;
import com.mcnedward.fittime.views.ExerciseView;

/**
 * Created by Edward on 2/1/2017.
 */

public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.ViewHolder> {
    private static final String TAG = SetListAdapter.class.getName();

    private Exercise mExercise;
    private Context mContext;
    private ExerciseView mExerciseView;

    public SetListAdapter(Context context, Exercise exercise, ExerciseView exerciseView) {
        mContext = context;
        mExercise = exercise;
        mExerciseView = exerciseView;
    }

    @Override
    public SetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rep, parent, false);
        return new ViewHolder(mContext, view, this);
    }

    @Override
    public void onBindViewHolder(SetListAdapter.ViewHolder holder, int position) {
        if (mExercise.getSets().size() == 0) return;
        Set rep = mExercise.getSets().get(position);
        holder.update(rep);
    }

    @Override
    public int getItemCount() {
        return mExercise.getSets().size();
    }

    private void removeSet(Set set) {
        mExercise.removeSet(set);
        notifyDataSetChanged();
        mExerciseView.onSetRemoved(set);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private Set mSet;
        private SetListAdapter mParent;

        /**
         * Creates the ViewHolder for the SetList items
         * @param context
         * @param itemView
         * @param setListAdapter Needs the parent so it can update when the delete button is pressed
         */
        ViewHolder(Context context, View itemView, SetListAdapter setListAdapter) {
            super(itemView);
            mParent = setListAdapter;
            mTextView = (TextView) itemView.findViewById(R.id.text_rep);
            ImageView mButton = (ImageView) itemView.findViewById(R.id.button_delete);
            Extension.setRippleBackground(context, mButton);
            mButton.setOnClickListener(v -> remove());
        }

        void update(Set set) {
            mSet = set;
            mTextView.setText(set.toString());
        }

        void remove() {
            mParent.removeSet(mSet);
        }
    }

}
