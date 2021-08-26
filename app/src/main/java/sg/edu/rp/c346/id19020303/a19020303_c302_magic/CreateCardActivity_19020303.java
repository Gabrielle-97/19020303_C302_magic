package sg.edu.rp.c346.id19020303.a19020303_c302_magic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateCardActivity_19020303 extends AppCompatActivity {

    private EditText etName, etCOLID, etType, etPrice, etQuantity;
    private Button btnAdd;
    private AsyncHttpClient client;
    private String colId, loginID, apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_19020303);

        etName = findViewById(R.id.editTextCardName);
        etCOLID = findViewById(R.id.editTextColourID);
        etType = findViewById(R.id.editTextTypeID);
        etQuantity = findViewById(R.id.editTextQuantity);
        etPrice = findViewById(R.id.editTextPrice);
        btnAdd = findViewById(R.id.buttonAdd);


        Intent x = getIntent();
        colId = x.getStringExtra("colID");
//        Toast.makeText(CreateCardActivity_19020303.this, colId+ "", Toast.LENGTH_SHORT).show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                client = new AsyncHttpClient();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CreateCardActivity_19020303.this);
                loginID = prefs.getString("loginID", "");
                apiKey = prefs.getString("apiKey", "");

                String colId = etCOLID.getText().toString();
                String type = etType.getText().toString();
                String cardPrice = etPrice.getText().toString();
                String cardQuantity = etQuantity.getText().toString();

                if (etName.getText().toString().equalsIgnoreCase(null) || colId.isEmpty() || type.isEmpty() || cardPrice.isEmpty() || cardQuantity.isEmpty()) {
                    Toast.makeText(CreateCardActivity_19020303.this, "Please fill up all the details.", Toast.LENGTH_LONG).show();
                    return;
                }

                else if (Integer.parseInt(colId) > 5 || Integer.parseInt(colId) < 1) {
                    Toast.makeText(CreateCardActivity_19020303.this, "Colour ID must be 1 to 5.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (Integer.parseInt(type) > 4 || Integer.parseInt(type) < 1) {
                    Toast.makeText(CreateCardActivity_19020303.this, "Type ID must be 1 to 4.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (Double.parseDouble(cardPrice) <0 ) {
                    Toast.makeText(CreateCardActivity_19020303.this, "Price must be higher than 0.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (Integer.parseInt(cardQuantity) <0) {
                    Toast.makeText(CreateCardActivity_19020303.this, "Quantity must be higher than 0.", Toast.LENGTH_LONG).show();
                    return;
                }

                RequestParams params = new RequestParams();
                params.add("cardName", etName.getText().toString());
                params.add("colourID", etCOLID.getText().toString());
                params.add("typeID", etType.getText().toString());
                params.add("quantity", etQuantity.getText().toString());
                params.add("price", etPrice.getText().toString());
                params.add("loginId", loginID);
                params.add("apikey", apiKey);
//                params.add("menu_item_category_id", colId);




                client.post("http://10.0.2.2/C302_magic/19020303_createCard.php", params, new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {

                            if(response.getString("success").equalsIgnoreCase("true")){
                                Toast.makeText(getApplicationContext(), "Card Created Successfully ", Toast.LENGTH_SHORT).show();
                                finish();
                            }

//                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Card Created Unsuccessfully ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }//end onSuccess


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_LONG).show();
                        System.out.println(responseString + "testingFailure");
                    }
                });
                finish();

            }
        });

    }
}