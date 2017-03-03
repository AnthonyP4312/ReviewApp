package grant.gawk.reviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RestaurantListActivity extends AppCompatActivity {
    ListView restaurantList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        //get reference to restaurant list and populate it.
        restaurantList = (ListView) findViewById(R.id.restaurant_list);
        populateList();




    }

    public void openSettings(MenuItem item) {
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).addToBackStack(null).commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        return true;
    }



    private void populateList(){
        adapter = new ArrayAdapter<>(this,
                //android.R.layout.simple_list_item_1, RestaurantData.getData(this.getApplicationContext())); //future
                android.R.layout.simple_list_item_1, RestaurantData.getData());

        setTitle("My Restaurants");
        restaurantList.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Log.d("onClick" , parent.toString());
                Log.d("onClick" , v.toString());
                Log.d("onClick" , Integer.toString(position));
                Log.d("onClick" , Long.toString(id));
                Log.d("onClick" , RestaurantData.getData().get((int)id));

                String restaurantName = RestaurantData.getData().get((int)id); //get name of selected restaurant
                adapter.notifyDataSetChanged();
                showRestaurantForm(v, restaurantName);

            }
        });


    }

    //called to move to Restaurant Data form.
    private void showRestaurantForm(View View,  String restaurantName){
        Intent intent = new Intent(this, DishListActivity.class);
        intent.putExtra("restaurantName", restaurantName);
        startActivity(intent);
    }

    public void addRestaurant(View view){
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        startActivity(intent);
    }

}
