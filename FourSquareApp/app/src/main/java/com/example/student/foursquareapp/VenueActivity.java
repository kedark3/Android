package com.example.student.foursquareapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VenueActivity extends AppCompatActivity implements GetEventFeed.contextInterface{

    final String CLIENT_ID="AVWR0CEXLDW5SV0RX0APEJNYGFXPGQMDSPRVADVGVV5IKN33";
    final String CLIENT_SECRET="MXNI5GGZRGQ20LU2VSI5X5Z4NHHBCKI55NGQOML5JGISRAN3";
    ArrayList<Venue> venueList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        /*https://api.foursquare.com/v2/venues/search?client_id=CLIENT_ID&client_secret=CLIENT_SECRET&v=20140806&near=CITY,STATE*/
        String CITY=getIntent().getStringExtra("cityState").split(",")[0];
        String STATE=getIntent().getStringExtra("cityState").split(",")[1];
        listView= (ListView) findViewById(R.id.listView);

        //Remember to customize Date

        new GetEventFeed(this).execute("https://api.foursquare.com/v2/venues/search?client_id="+
                CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&v=20160321&near="+CITY+","+STATE);


    }

    @Override
    public Context getcontext() {
        return VenueActivity.this;
    }

    @Override
    public void setupData(final ArrayList<Venue> venue) {

        final SharedPreferences pref= getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=pref.edit();
        venueList=venue;
        for(Venue v:venueList){
            if(pref.getString(v.getVenueId(),null)!=null)
                venue.get(venue.indexOf(v)).setVisited(true);
        }



        final VenueAdapter adapter = new VenueAdapter(this, R.layout.row_item_layout, venueList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(pref.getString(venueList.get(position).getVenueId(),null)==null) {
                    editor.putString(venueList.get(position).getVenueId(), venueList.get(position).getVenueName());
                    editor.commit();
                    Toast.makeText(VenueActivity.this, "Successfully Marked", Toast.LENGTH_SHORT).show();
                    venueList.get(position).setVisited(true);
                    adapter.notifyDataSetChanged();
                }else {
                    editor.remove(venueList.get(position).getVenueId());
                    editor.commit();
                    Toast.makeText(VenueActivity.this, "Successfully Un-Marked", Toast.LENGTH_SHORT).show();
                    venueList.get(position).setVisited(false);
                    adapter.notifyDataSetChanged();
                }

                return false;
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_at_venue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ArrayList<Venue> markedVenueList=new ArrayList<>();
        final SharedPreferences pref= getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=pref.edit();
        VenueAdapter adapter;
        switch (item.getItemId()) {

            case R.id.view_marked:
                for(Venue v: venueList) {
                    if(v.isVisited())
                        markedVenueList.add(v);
                }
                adapter = new VenueAdapter(this, R.layout.row_item_layout, markedVenueList);
                listView.setAdapter(adapter);

                return true;

            case R.id.clear_all:
                for(Venue v: venueList){
                    if(pref.getString(v.getVenueId(),null)!=null)
                        editor.remove(v.getVenueId());
                    v.setVisited(false);
                }
                adapter = new VenueAdapter(this, R.layout.row_item_layout, venueList);
                listView.setAdapter(adapter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
