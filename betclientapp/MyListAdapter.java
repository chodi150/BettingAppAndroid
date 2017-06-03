package chodi.com.betclientapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by Użytkownik on 27.02.2017.
 */
public class MyListAdapter extends ArrayAdapter<String>
{
    private int layout;
    final int TYPE_FOR_TEAM_1 = 1;
    final int TYPE_FOR_TEAM_2 = 2;
    final int NO_TYPE = 0;
    final int TYPE_COLUMN = 1;

    public MyListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }
    @Override

    public int getViewTypeCount() {
        return getCount()<1 ? 1 : getCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder =null;
        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent,false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.teamsTxt = (TextView)convertView.findViewById(R.id.teamsTxt);
            viewHolder.team2Tbtn=(ToggleButton)convertView.findViewById(R.id.team2Tbtn);
            viewHolder.team1Tbtn=(ToggleButton)convertView.findViewById(R.id.team1Tbtn);
            viewHolder.team1Tbtn.setText("Team 1");
            viewHolder.team2Tbtn.setText("Team 2");
            viewHolder.team1Tbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        viewHolder.team2Tbtn.setChecked(false);
                        UserData.types[position][TYPE_COLUMN]=TYPE_FOR_TEAM_1;
                    }
                    else
                    {
                        UserData.types[position][TYPE_COLUMN]=NO_TYPE;
                    }
                }
            });
            viewHolder.team2Tbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        viewHolder.team1Tbtn.setChecked(false);
                        UserData.types[position][TYPE_COLUMN]=TYPE_FOR_TEAM_2;

                    }
                    else
                    {
                        UserData.types[position][TYPE_COLUMN]=NO_TYPE;
                    }
                }
            });
            convertView.setTag(viewHolder);
        }

        mainViewHolder = (ViewHolder)convertView.getTag();
        mainViewHolder.teamsTxt.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder
    {
        TextView teamsTxt;
        ToggleButton team1Tbtn;
        ToggleButton team2Tbtn;
    }
}

