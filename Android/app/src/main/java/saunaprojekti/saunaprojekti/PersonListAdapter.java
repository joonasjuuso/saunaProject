package saunaprojekti.saunaprojekti;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonListAdapter extends ArrayAdapter<Sauna> {

    private static final String TAG = "PersonListAdapter";

    private final Context mContext;
    private final int mResource;
    private final int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        public TextView paivamaara;
        public TextView aika;
        public TextView lampotila;
        public TextView onko;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PersonListAdapter(Context context, int resource, ArrayList<Sauna> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String paivamaara = getItem(position).getPAIVAMAARA();
        String aika = getItem(position).getAIKA();
        String lampotila = getItem(position).getLAMPOTILA();
        String onko = getItem(position).getONKO();

        //Create the person object with the information
        Sauna person = new Sauna(paivamaara,aika,lampotila,onko);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.record, null);
            holder= new ViewHolder();
            holder.paivamaara = convertView.findViewById(R.id.record_paivamaara);
            holder.aika = convertView.findViewById(R.id.record_aika);
            holder.lampotila = convertView.findViewById(R.id.record_lampotila);
            holder.onko = convertView.findViewById(R.id.record_onko);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }



        holder.paivamaara.setText(person.getPAIVAMAARA());
        holder.aika.setText(person.getAIKA());
        holder.lampotila.setText(person.getLAMPOTILA() + " °C");
        holder.onko.setText(person.getONKO());


        return convertView;
    }
}
