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

public class RepListAdapter extends RecyclerView.Adapter<RepListAdapter.ViewHolder> {
    private static final String TAG = RepListAdapter.class.getName();

    private Exercise mExercise;
    private Context mContext;
    private ExerciseView mExerciseView;

    public RepListAdapter(Context context, Exercise exercise, ExerciseView exerciseView) {
        mContext = context;
        mExercise = exercise;
        mExerciseView = exerciseView;
    }

    @Override
    public RepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rep, parent, false);
        return new ViewHolder(mContext, view, this);
    }

    @Override
    public void onBindViewHolder(RepListAdapter.ViewHolder holder, int position) {
        if (mExercise.getSets().size() == 0) return;
        Set rep = mExercise.getSets().get(position);
        holder.update(rep);
    }

    @Override
    public int getItemCount() {
        return mExercise.getSets().size();
    }

    private void removeRep(Set rep) {
        mExercise.removeSet(rep);
        notifyDataSetChanged();
        mExerciseView.onRepRemoved();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private Set mRep;
        private RepListAdapter mParent;

        /**
         * Creates the ViewHolder for the RepList items
         * @param context
         * @param itemView
         * @param repListAdapter Needs the parent so it can update when the delete button is pressed
         */
        ViewHolder(Context context, View itemView, RepListAdapter repListAdapter) {
            super(itemView);
            mParent = repListAdapter;
            mTextView = (TextView) itemView.findViewById(R.id.text_rep);
            ImageView mButton = (ImageView) itemView.findViewById(R.id.button_delete);
            Extension.setRippleBackground(context, mButton);
            mButton.setOnClickListener(v -> remove());
        }

        void update(Set rep) {
            mRep = rep;
            mTextView.setText(rep.toString());
        }

        void remove() {
            mParent.removeRep(mRep);
        }
    }

}
