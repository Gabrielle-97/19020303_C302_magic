package sg.edu.rp.c346.id19020303.a19020303_c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
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

public class CardActivity_19020303 extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<MenuColours> adapter;
    private ArrayList<MenuColours> list;
    private AsyncHttpClient client;
    private String colId, loginID, apiKey;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_19020303);

        listView = findViewById(R.id.lvCardActivity);

        client = new AsyncHttpClient();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loginID = prefs.getString("loginID", "");
        apiKey = prefs.getString("apiKey", "");
        userRole = prefs.getString("role", "");

        if (loginID.equalsIgnoreCase("") || apiKey.equalsIgnoreCase("")){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        Intent x = getIntent();
        colId = x.getStringExtra("colours_id");

    }

    @Override
    protected void onResume() {
        super.onResume();
        list = new ArrayList<MenuColours>();
        RequestParams params = new RequestParams();
        params.add("loginId", loginID);
        params.add("apikey", apiKey);
        params.add("colID", colId);
        params.add("role", userRole);


        client.post("http://10.0.2.2/C302_magic/19020303_getCardsByColour.php", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try{
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonOBJ = response.getJSONObject(i);

                        String cardID = jsonOBJ.getString("cardId");
                        String colourID = jsonOBJ.getString("colourId");
                        String typeID = jsonOBJ.getString("typeId");
                        String card = jsonOBJ.getString("cardName");
                        int quantity = jsonOBJ.getInt("quantity");
                        double unitPrice = jsonOBJ.getDouble("price");


                        MenuColours menuCategory = new MenuColours( cardID, colourID , card , typeID, quantity ,unitPrice);
                        list.add(menuCategory);
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }


                adapter = new ArrayAdapter<MenuColours>(CardActivity_19020303.this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
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