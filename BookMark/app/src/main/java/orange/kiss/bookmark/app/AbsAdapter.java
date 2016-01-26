package orange.kiss.bookmark.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import orange.kiss.bookmark.view.ViewHolder;

/**
 * Created by Administrator on 2016/1/26.
 */
public abstract class AbsAdapter<T> extends BaseAdapter{
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public AbsAdapter(Context context,List<T> datas,int itemlayoutId){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mItemLayoutId = itemlayoutId;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = getViewHolder(convertView,parent,position);
        convertView(vh,(T)(getItem(position)));
        return vh.getConvertView();
    }
    public abstract void convertView(ViewHolder viewHolder,T item);

    public ViewHolder getViewHolder(View convertView,ViewGroup parent,int postion){
        return ViewHolder.get(mContext,convertView,parent,mItemLayoutId,postion);
    }

}
