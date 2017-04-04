package grant.gawk.reviewapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * <p>
 *      The activity to display the list of dishes for a particular restaurant
 * </p>
 * @author Anthony, Warren
 * @version 1.0
 * @since 1.0
 * @see FileHandler
 */
public class DishListActivity extends AppCompatActivity {
    /**
     * <p>
     *     Attribute to hold the ListView object from the activity
     * </p>
     */
    ListView dishList;

    /**
     * <p>
     *     Attribute to hold the name of the particular restaurant this list is for
     * </p>
     */
    String restaurantName;
    Long restaurantID;
    ArrayAdapter<Dish> adapter;
    Context appContext;
    DataAccessObject dao;


    /**
     * <p>
     *      Unwraps the intent from RestaurantListActivity to get the restaurant name.
     *      Also sets the title to the restaurant name and initializes the appContext, as well
     *      as the dishList and restaurantName fields.
     *      <br />
     *      <br />
     *      Makes a call to populateList() to initialize the listView's contents.
     * </p>
     * @param savedInstanceState  The bundle object passed by the calling function
     * @see RestaurantListActivity
     * @see DishListActivity#populateList()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);
        appContext = getApplicationContext();
        dao = new DataAccessObject(appContext);
        dao.open();

        //Sets Restaurant's name to toolbar title
        Intent intent = getIntent();
        Log.d("dishCLick", intent.getStringExtra("restaurantName"));
        setTitle(intent.getStringExtra("restaurantName"));
        restaurantName = intent.getStringExtra("restaurantName");
        restaurantID = intent.getLongExtra("restaurantID", 0L);


        dishList = (ListView) findViewById(R.id.dish_list);

        populateList();
    }

    /**
     * <p>
     *     Updates the list on activity resume with a call to populateList().
     * </p>
     * @see DishListActivity#populateList()
     */
    @Override
    protected void onResume() {
        super.onResume();
        dao.open();
        populateList();
    }

    @Override
    protected void onPause(){
        dao.close();
        super.onPause();
    }

    /**
     * <p>
     *     This method populates the data in the list. It use uses an on click listener to
     *     record if the user has interacted with the list, then takes the index, copies the
     *     dish object at that index in the ArrayList dishes, and sends the dish to the ShowDishActivity.
     * </p>
     *
     * <p>
     *     Makes a call to getData() to refresh the list.
     * </p>
     * @see ShowDishActivity
     * @see DishListActivity#getData()
     * @see DishListActivity#showDishForm
     */
    private void populateList() {
        //get list of restaurants from Database and adapt them to list view
        final List<Dish> dishes = dao.getDishesFromRestaurant(restaurantID);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dishes);
        dishList.setAdapter(adapter);

        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Log.d("Dish List Item" , dishes.get((int)id).toString());


                Dish clickedDish = dishes.get((int)id);
                adapter.notifyDataSetChanged();
                showDishForm(clickedDish);
            }
        });

    }

    /**
     * <p>
     *     Overrides functionality to create overflow menu on toolbar
     * </p>
     * @param menu The Menu object passed by the calling function
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        return true;
    }

    /**
     * <p>
     *     Our onClick function for the settings menu. Opens our settings fragment.
     * </p>
     * @param item The MenuItem object passed by the calling function
     */
    public void openSettings(MenuItem item) {
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).addToBackStack(null).commit();
    }


    //called to move to Restaurant Data form.
    private void showDishForm(Dish dish){

        Intent intent = new Intent(this, ShowDishActivity.class);
        intent.putExtra("dishName", dish.getName());
        intent.putExtra("dishDate", dish.getDate());
        intent.putExtra("dishComments", dish.getComments());
        intent.putExtra("dishRating", Float.toString(dish.getRating()));
        startActivity(intent);

    }

    /**
     * <p>
     *     Sends the restaurant name as the extra in an intent, starts AddDishActivity
     * </p>
     * @param view The DishlistActivity's View
     * @see AddDishActivity
     */
    public void addDish(View view) {
        Intent intent = new Intent(this, AddDishActivity.class);
        intent.putExtra("restaurantName", restaurantName);
        intent.putExtra("restaurantID", restaurantID);
        startActivity(intent);
    }


}
