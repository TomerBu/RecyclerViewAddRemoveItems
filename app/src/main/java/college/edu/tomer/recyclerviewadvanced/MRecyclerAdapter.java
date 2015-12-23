package college.edu.tomer.recyclerviewadvanced;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tomer. Licence GPL 3.0.
 */
public class MRecyclerAdapter extends RecyclerView.Adapter<MRecyclerAdapter.MViewHolder> implements View.OnClickListener {

    /*
        Private members:
        */
    private ArrayList<String> data;
    private final RecyclerView mRecyclerView;
    private boolean shouldAnimate = true;

    //Constructor
    public MRecyclerAdapter(ArrayList<String> data, RecyclerView mRecyclerView) {
        this.data = data;
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                shouldAnimate = true;
            }
        });
    }


    //Update the itemView from the viewHolder to display the data at position
    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        holder.tvData.setText(data.get(position));

        View itemView = holder.itemView;

        if (shouldAnimate) {
            //Prep Animation
            ViewCompat.setScaleX(itemView, 0);
            ViewCompat.setScaleY(itemView, 0);
            //Animate
            ViewCompat.animate(itemView).scaleX(1).scaleY(1);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //Inflate The Xml into a view and init a viewHolder for findViewByID
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.rv_list_item, parent, false);

        MViewHolder vHolder = new MViewHolder(itemView);
        vHolder.btnRemove.setOnClickListener(this);
        return vHolder;
    }

    public void addNew(String item) {
        shouldAnimate = false;
        data.add(item);
        notifyItemInserted(data.size() - 1);
        mRecyclerView.scrollToPosition(data.size() - 1);
    }

    @Override
    public void onClick(View v) {
        //Remove Item clicked
        int position = mRecyclerView.getChildAdapterPosition((View) v.getParent());
        if (position != -1) {
            shouldAnimate = false;
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Find All Views by ID Once and for good
     **/
    public static class MViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvData;
        Button btnRemove;

        //* More Views here...
        public MViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvData = (TextView) itemView.findViewById(R.id.tvData);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove);
        }
    }
}
