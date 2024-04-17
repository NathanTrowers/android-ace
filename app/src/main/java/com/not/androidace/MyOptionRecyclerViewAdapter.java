package com.not.androidace;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import com.not.androidace.databinding.FragmentOptionBinding;
import com.not.androidace.placeholder.OptionItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link OptionItem}.
 *
 * Display received options data
 * Show when option selected and report selection to OptionFragment.
 */
public class MyOptionRecyclerViewAdapter extends RecyclerView.Adapter<MyOptionRecyclerViewAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private int mSelectdPosition = -1;
    private final List<OptionItem> mValues;

    public MyOptionRecyclerViewAdapter(List<OptionItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentOptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        @ColorInt
        int backgroundColor, textColor;
        if (position == mSelectdPosition) {
            backgroundColor = Color.argb(100,0,0,0);
            textColor = Color.argb(100,255,255,255);
            holder.mIdView.setBackgroundColor(backgroundColor);
            holder.mIdView.setTextColor(textColor);
            holder.mContentView.setBackgroundColor(backgroundColor);
            holder.mContentView.setTextColor(textColor);

            String optionNumber = holder.mIdView.getText().toString();
            QuestionFiveFragment.responseNumber = Integer.parseInt(optionNumber);
        } else {
            backgroundColor = Color.argb(100,233,30,99);
            textColor = Color.argb(100,0,0,0);
            holder.mIdView.setBackgroundColor(backgroundColor);
            holder.mIdView.setTextColor(textColor);
            holder.mContentView.setBackgroundColor(backgroundColor);
            holder.mContentView.setTextColor(textColor);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setSelectedPosition(int position) {
        mSelectdPosition = position;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

     public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
     }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public OptionItem mItem;

        public ViewHolder(FragmentOptionBinding binding, final OnItemClickListener listener) {
          super(binding.getRoot());
          mIdView = binding.itemNumber;
          mContentView = binding.content;
          View option = binding.getRoot();
          option.setOnClickListener(v -> {
                  if (listener != null) {
                      listener.onItemClick(getAbsoluteAdapterPosition());
                  }
              }
          );
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
