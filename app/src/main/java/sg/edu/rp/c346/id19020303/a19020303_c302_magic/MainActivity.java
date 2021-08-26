package sg.edu.rp.c346.id19020303.a19020303_c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Colours> adapter;
    private ArrayList<Colours> list;
    private String loginId;
    private String apikey;
    private String colId;
    private String userRole;
    private AsyncHttpClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lvColours);
        list = new ArrayList<Colours>();
        adapter = new ArrayAdapter<Colours>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        client = new AsyncHttpClient();

        //TODO: read loginId and apiKey from SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loginID = prefs.getString("loginID", "");
        String apiKey = prefs.getString("apiKey", "");
        String role = prefs.getString("role", "");
//        System.out.println("test: "+loginID+apiKey+role);
//        Toast.makeText(getApplicationContext(), loginID , Toast.LENGTH_SHORT).show();


        // TODO: if loginId and apikey is empty, go back to LoginActivity
        if (loginID.equalsIgnoreCase("") || apiKey.equalsIgnoreCase("")){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        //TODO: Point X - call getMenuCategories.php to populate the list view
        RequestParams params = new RequestParams();
        params.add("loginId", loginID);
        params.add("apikey", apiKey);
        params.add("role", role);
        userRole = role;



        client.post("http://10.0.2.2/C302_magic/19020303_getColours.php", params, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                System.out.println(response.toString() + "error is here ");
//            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try{
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonOBJ = response.getJSONObject(i);

                        String colourID = jsonOBJ.getString("colourId");
                        String colourName = jsonOBJ.getString("colourName");

                        Colours coloursCategory = new Colours(colourID + "", colourName);
                        list.add(coloursCategory);
                        adapter.notifyDataSetChanged();
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }


                adapter.notifyDataSetChanged();
            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Colours selected = list.get(position);

                //TODO: make Intent to DisplayMenuItemsActivity passing the categoryId
                Intent i = new Intent(MainActivity.this,CardActivity_19020303.class);
                i.putExtra("colours_id", selected.getColoursID());
                startActivity(i);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(userRole.equalsIgnoreCase("customer")) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        else if(userRole.equalsIgnoreCase("admin")){
            getMenuInflater().inflate(R.menu.submain, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            // TODO: Clear SharedPreferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().clear();
            prefs.edit().commit();

            // TODO: Redirect back to login screen
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

            return true;
        }

        else if (id == R.id.menu_cardsbycolors){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;

        }

        else if (id == R.id.menu_addmenuitem){
            Intent intent = new Intent(getApplicationContext(), CreateCardActivity_19020303.class);
            intent.putExtra("colID", colId);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}