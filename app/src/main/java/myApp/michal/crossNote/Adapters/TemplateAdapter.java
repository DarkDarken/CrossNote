package myApp.michal.crossNote.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TemplateAdapter<T extends Enum<T>> extends BaseAdapter {
    private LayoutInflater inflater;
    private int resItemLayout;
    private int idTextLayout;
    private int resDropItemLayout;
    private int idTextDrop;

    private Class<T> category;

    private T[] values(){
        return category.getEnumConstants();
    }

    public TemplateAdapter(Context context, int resItemLayout, int idTextLayout, int resDropItemLayout, int idTextDrop, Class<T> category) {
        this.category = category;
        this.resItemLayout = resItemLayout;
        this.idTextLayout = idTextLayout;
        this.resDropItemLayout = resDropItemLayout;
        this.idTextDrop = idTextDrop;
        inflater = LayoutInflater.from(context);
    }

    private String getNames(int position){
        return values()[position].name().replace("_", " ");
    }

    @Override
    public int getCount() {return values().length; }

    @Override
    public T getItem(int position) {
        return values()[position];
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resItemLayout, null);
        }
        TextView textView = convertView.findViewById(idTextLayout);
        textView.setText(getNames(position));
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(resDropItemLayout, null);
        }
        TextView textView = convertView.findViewById(idTextDrop);
        textView.setText(getItem(position).name().replace("_", " "));
        textView.setPadding(30, 0, 0, 0);
        return convertView;
    }
}

