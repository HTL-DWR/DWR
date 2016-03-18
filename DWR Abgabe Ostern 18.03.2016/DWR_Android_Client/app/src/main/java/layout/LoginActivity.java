package layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stefan.dwr_neu.MainActivity;
import com.example.stefan.dwr_neu.Model.Credentials;
import com.example.stefan.dwr_neu.Connectivity.Database;
import com.example.stefan.dwr_neu.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnLogin=null;
    private TextView txtUsername=null;
    private TextView txtPassword=null;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);




        db=Database.newInstance();
        initGUIComponents();
    }

    private void initGUIComponents()
    {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername=(TextView)findViewById(R.id.txtUsername);
        txtPassword=(TextView)findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==btnLogin.getId())
        {
            db.setCredentials(new Credentials(txtUsername.getText().toString(), txtPassword.getText().toString()));
            try {

                db.setSession();
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);


            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }


}

