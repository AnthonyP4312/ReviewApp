package grant.gawk.reviewapp;


import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

public class AddRestaurantActivity extends AppCompatActivity implements OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    Context appContext;
    ImageView getImage;
    Button btnSubmit;
    Button takePicture;
    EditText restName, cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        setTitle("Add a Restaurant");
        appContext = this.getApplicationContext();
        cityName = (EditText) findViewById(R.id.cityName);
        restName = (EditText) findViewById(R.id.restName);
    }
//test

    public void takePicture(View v)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //Allow the gallery to be opened
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.takePicture:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //Allow the gallery to be opened
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.btnSubmit:
                String city = cityName.getText().toString();
                String restaurantName = restName.getText().toString();
                FileHandler files = new FileHandler(appContext);
                Restaurant restaurant = new Restaurant(null, restaurantName, city);
                files.writeRestaurant(restaurant);

                Intent returnToRestaurantList = new Intent(this, RestaurantListActivity.class);
                startActivity(returnToRestaurantList);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data.getData() != null) //make sure to receive result from gallery
        {
            Uri selectedImage = data.getData(); //Get the uri, allows to prefer back to the image
            System.out.println(selectedImage.getPath());
            getImage.setImageURI(selectedImage); //display the image
        }
    }

}

