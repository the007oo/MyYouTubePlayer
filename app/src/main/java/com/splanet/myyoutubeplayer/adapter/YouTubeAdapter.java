package com.splanet.myyoutubeplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.splanet.myyoutubeplayer.R;
import com.splanet.myyoutubeplayer.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phattarapong on 2/20/2018.
 */

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.ViewHolder> {

    private Context context;
    private List<Video> searchResults;
    private onSetClickListener onSetClickListener;
    private int lastPosition = -1;

    public YouTubeAdapter(Context context) {
        this.context = context;
        this.searchResults = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Video video = searchResults.get(position);

        setAnimation(holder.itemView, position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();
        requestOptions.error(android.R.drawable.ic_menu_report_image);
        requestOptions.placeholder(android.R.drawable.ic_menu_report_image);

        Glide.with(context)
                .load(video.getThumbnailURL())
                .apply(requestOptions)
                .into(holder.videothumbnail);

        holder.videodescription.setText(video.getDescription());
        holder.videotitle.setText(video.getTitle());

        holder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSetClickListener != null) {
                    onSetClickListener.onSetClickListener(video.getId());
                }
            }
        });

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_form_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void setOnSetClickListener(YouTubeAdapter.onSetClickListener onSetClickListener) {
        this.onSetClickListener = onSetClickListener;
    }

    public interface onSetClickListener {
        void onSetClickListener(String id);
    }

    public void addItem(List<Video> searchResults) {
        if (searchResults == null) {
            searchResults = new ArrayList<>();
        }
        this.searchResults = searchResults;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return searchResults.size() != 0 ? searchResults.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView videodescription;
        private TextView videotitle;
        private ImageView videothumbnail;
        private RelativeLayout contentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            videodescription = (TextView) itemView.findViewById(R.id.video_description);
            videotitle = (TextView) itemView.findViewById(R.id.video_title);
            videothumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            contentLayout = (RelativeLayout) itemView.findViewById(R.id.content_layout);
        }
    }
}
