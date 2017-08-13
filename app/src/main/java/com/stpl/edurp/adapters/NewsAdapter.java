package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.models.TableNewsMasterDataModel;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private static final java.lang.String TAG = "NewsAdapter";
    private Context mContext;
    private ArrayList<TableNewsMasterDataModel> mList;
    private View.OnClickListener mListener;

    public NewsAdapter(Context context, ArrayList<TableNewsMasterDataModel> pList, View.OnClickListener pListener) {
        mContext = context;
        mList = pList;
        mListener = pListener;
        AppLog.log(TAG, " +++ NewsAdapter +pList.size()+++ " + pList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.fragment_news_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppLog.log(TAG, " +++ onBindViewHolder ++++ ");
        //String url ="https://edurpstorage.blob.core.windows.net/edurpcontainer/DEV/1/61/103?sv=2015-12-11&sr=b&sig=kPNs9zkQw0v1bqiNnvBkpjRY40ve6qm0%2BDak3zl26Xk%3D&se=2017-03-04T14%3A27%3A34Z&sp=rwl&rscd=attachment%3B%20filename%3Dimages.jpg";
        // GetPicassoImage.setImage(mContext,url,holder.imageViewRhumbnil);
        GetUILImage.getInstance().setGallaryImage(mContext, mList.get(position).getThumbNailPath(), holder.imageViewRhumbnil, R.drawable.image_placeholder);
        holder.textViewPublishBy.setText(mList.get(position).getPublishedBy());
        holder.textViewRefTitle.setText(mList.get(position).getReferenceTitle());
        holder.textViewPublishedTime.setText(mList.get(position).getPublishedOn());
        holder.webviewShortBody.loadData(mList.get(position).getShortBody(), "text/html; charset=utf-8", "utf-8");
        holder.webviewShortBody.getSettings().setJavaScriptEnabled(true);
        //holder.textViewTag.setText(mList.get(position).getMenuCode());
        holder.textViewLike.setText(mList.get(position).getTotalLikes());
        holder.textViewComment.setText(mList.get(position).getTotalComments());
        //--------------------------------------------------------------
        holder.lin_holder.setOnClickListener(mListener);
        holder.lin_holder.setTag(position);
        //Utils.langConversion(mContext, holder.textViewTag, WSContant.TAG_LANG_NEWS, mList.get(position).getMenuCode(), UserInfo.lang_pref);
        holder.textViewTag.setText(Utils.getLangConversion(WSContant.TAG_LANG_NEWS, mList.get(position).getMenuCode(), UserInfo.lang_pref));
        AppLog.log("getLikedByMe ",""+mList.get(position).getLikedByMe());
        if (mList.get(position).getLikedByMe() >= 1) {
            holder.imageViewLike.setImageResource(R.drawable.comments_like_icon_active);
        } else {
            holder.imageViewLike.setImageResource(R.drawable.comments_like_icon);
        }

        //add attachment icon if News has

        holder.textViewRefTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.attachment, 0);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lin_holder;
        private final TextView textViewRefTitle;
        private final TextView textViewPublishBy;
        private final TextView textViewPublishedTime;
        private final ImageView imageViewRhumbnil;
        private final WebView webviewShortBody;
        private final ImageView imageViewTag;
        private final ImageView imageViewLike;
        private final ImageView imageViewComment;
        private final TextView textViewTag;
        private final TextView textViewLike;
        private final TextView textViewComment;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewRhumbnil = (ImageView) itemView.findViewById(R.id.imageview_news_row_thumbnil);
            textViewRefTitle = (TextView) itemView.findViewById(R.id.textview_news_row_reference_title);
            textViewPublishBy = (TextView) itemView.findViewById(R.id.textview_news_row_published_by);
            textViewPublishedTime = (TextView) itemView.findViewById(R.id.textview_news_row_published_time);
            webviewShortBody = (WebView) itemView.findViewById(R.id.webview_news_row_shortbody);

            imageViewTag = (ImageView) itemView.findViewById(R.id.imageview_bottom_tag);
            imageViewLike = (ImageView) itemView.findViewById(R.id.imageview_bottom_like);
            imageViewComment = (ImageView) itemView.findViewById(R.id.imageview_bottom_comment);
            textViewTag = (TextView) itemView.findViewById(R.id.textview_inc_bottom_tag);
            textViewLike = (TextView) itemView.findViewById(R.id.textview_inc_bottom_like);
            textViewComment = (TextView) itemView.findViewById(R.id.textview_inc_bottom_comment);

            webviewShortBody.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            webviewShortBody.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webviewShortBody.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

            lin_holder = (LinearLayout)itemView.findViewById(R.id.lin_news_row_holder);
        }
    }
}
