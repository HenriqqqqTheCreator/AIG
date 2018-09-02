package zapzap.tccetec.com.aig.licao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zapzap.tccetec.com.aig.LoginActivity;
import zapzap.tccetec.com.aig.MenuActivity;
import zapzap.tccetec.com.aig.ProvaFinalHEHEActivity;
import zapzap.tccetec.com.aig.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;

    public RecyclerViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        holder.tv__book_title.setText(mData.get(position).getTitle());
        holder.book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.tv_my_friend.setText(mData.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, TemaActivity.class);

                // passing data to the book activity
                intent.putExtra("Title", mData.get(position).getTitle());
                intent.putExtra("Description", mData.get(position).getDescription());
                intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                intent.putExtra("CorFundo", mData.get(position).getColorBook());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv__book_title;
        ImageView book_thumbnail;
        CardView cardView;
        TextView tv_my_friend;

        public MyViewHolder(View itemView) {
            super(itemView);

            //tv__book_title = itemView.findViewById(R.id.hello_my_friend);
            book_thumbnail = itemView.findViewById(R.id.img_my_friend);
            cardView = itemView.findViewById(R.id.cardview_id);
            tv_my_friend = itemView.findViewById(R.id.tv_my_friend);

        }
    }

}
