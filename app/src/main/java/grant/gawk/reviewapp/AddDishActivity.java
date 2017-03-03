package grant.gawk.reviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.view.View;
import java.text.DateFormat;
import java.util.Date;
import android.content.Intent;
import android.content.Context;

public class AddDishActivity extends AppCompatActivity {
    EditText dishNameWidget;
    EditText commentsWidget;
    EditText dateWidget;
    RatingBar dishRatingWidget;
    String restaurantName;
    Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        setTitle("Add a Dish");
        appContext = this.getApplicationContext();
        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("restaurantName");

        dishNameWidget = (EditText) findViewById(R.id.dishNameEntry);
        commentsWidget = (EditText) findViewById(R.id.commentTextBox);
        dateWidget = (EditText) findViewById(R.id.dateEntry);
        dishRatingWidget = (RatingBar) findViewById(R.id.dishRatingBar);

        //Date autogeneration
        Date newDate = new Date();
        String date = DateFormat.getDateInstance().format(newDate);
        dateWidget.setText(date);
    }

    public void collectData(View view)
    {
        //collected Data
        String dishName = dishNameWidget.getText().toString();
        String comments = commentsWidget.getText().toString();
        String date = dateWidget.getText().toString();
        float dishRating = dishRatingWidget.getRating();

        // create new Dish object to hold the data
        Dish newDish = new Dish(dishName, date, comments, dishRating);

        /* portion to interface FileHandler should go here. Namely, feed it the new Dish, so to speak */
        FileHandler files = new FileHandler(appContext);
        files.writeDish(restaurantName, newDish);
        /* End File Handler Block */

        Intent transition = new Intent(this, DishListActivity.class);
        transition.putExtra("restaurantName", restaurantName);
        startActivity(transition);

    }
}
