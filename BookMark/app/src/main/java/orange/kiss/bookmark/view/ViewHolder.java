package orange.kiss.bookmark.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import orange.kiss.bookmark.R;

/**
 * Created by Administrator on 2016/1/26.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private ViewHolder(Context context,ViewGroup parent,int layoutId,int position){
        this.mPosition = position;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        this.mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }
    public static ViewHolder get(Context context,View convertView, ViewGroup parent, int layoutId, int position){
        if(convertView==null){
            return new ViewHolder(context,parent,layoutId,position);
        }
        return (ViewHolder)convertView.getTag();
    }
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }
    public View getConvertView(){
        return mConvertView;
    }
    public ViewHolder setText(int viewId,String text){
        TextView tv = (TextView) mViews.get(viewId);
        tv.setText(text);
        return this;
    }
    public ViewHolder setImageResource(int viewId,int drawable){
        ImageView iv = (ImageView) mViews.get(viewId);
        iv.setImageResource(drawable);
        return this;
    }
    public ViewHolder setImageBitmap(int viewId,Bitmap btm){
        ImageView iv = (ImageView) mViews.get(viewId);
        iv.setImageBitmap(btm);
        return this;
    }
}
