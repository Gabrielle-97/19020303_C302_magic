
package sg.edu.rp.c346.id19020303.a19020303_c302_magic;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etLoginID, etPassword;
    private Button btnSubmit;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginID = (EditText)findViewById(R.id.editTextLoginID);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        btnSubmit = (Button)findViewById(R.id.buttonSubmit);

        client = new AsyncHttpClient();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginID.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter username.", Toast.LENGTH_LONG).show();

                } else if (password.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

                } else {
                    //proceed to authenticate user
                    OnLogin(v);
                }
            }
        });
    }

    private void OnLogin(View v) {
        RequestParams params = new RequestParams();
        params.add("username", etLoginID.getText().toString());
        params.add("password", etPassword.getText().toString());



        client.post("http://10.0.2.2/C302_magic/userAuthentication.php", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //called when response HTTP status is "200 OK"

                try{
                    if(response.get("authenticated").toString().equals("false")){
                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                    } else {
                        String loginID = response.get("id").toString();
                        String apiKey = response.get("apikey").toString();
                        String role = response.getString("role");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("loginID", loginID);
                        editor.putString("apiKey", apiKey);
                        editor.putString("role", role);
                        editor.commit();
                        Intent a = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(a);

                    }


                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }
}


