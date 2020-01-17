package activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koobookandroidapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.UserController;
import dataaccess.setup.AppDatabase;
import entities.User;

public class SignUpActivity extends AppCompatActivity {
    TextView textView_firstName_error_msg;
    TextView textView_email_error_msg;
    TextView textView_password_error_msg;
    TextView textView_passwords_error_msg;
    EditText editText_firstName;
    EditText editText_email;
    EditText editText_password;
    EditText editText_confirmPassword;
    ArrayList<EditText> passwords;
    HashMap<EditText,TextView> map;
    Boolean doPasswordsMatch;
    AppDatabase db;
    HashMap<Integer,String> userDetails;
    boolean firstNameValidated;
    boolean emailValidated;
    boolean passwordValidated;
    boolean confirmPasswordValidated;
    boolean passwordsValidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

        map = new HashMap<EditText, TextView>();



        textView_firstName_error_msg = findViewById(R.id.textview_firstname_error_msg);
        editText_firstName = findViewById(R.id.edittext_firstname);
        map.put(editText_firstName, textView_firstName_error_msg);

        textView_email_error_msg = findViewById(R.id.textview_email_error_msg);
        editText_email = findViewById(R.id.edittext_email);
        map.put(editText_email, textView_email_error_msg);


        textView_password_error_msg = findViewById(R.id.textview_password_error_msg);
        editText_password = findViewById(R.id.edittext_password);
        map.put(editText_password, textView_password_error_msg);


        editText_confirmPassword = findViewById(R.id.edittext_confirmpassword);
        textView_passwords_error_msg = findViewById(R.id.textview_passwords_error_msg);

        //I added the "Password does not match text view to the hashmap because I plan to access it
        // and modify the string depending on whether the userId entered mismatching passwords
        // or didn't enter anything at all."
        map.put(editText_confirmPassword, textView_passwords_error_msg);

        passwords = new ArrayList<>();
        passwords.add(editText_password);
        passwords.add(editText_confirmPassword);


    }

    //After clicking the sign up, it checks on whether each text field contains a valid value. If the validation checks pass
    // then the process of storing the data in the databases can proceed.
    public void signUpButtonClicked(View v){

        UserController userController = new UserController();

        firstNameValidated = userController.validateFirstName(editText_firstName,textView_firstName_error_msg);
        emailValidated = userController.validateEmail(editText_email,textView_email_error_msg);
        passwordValidated = userController.validatePassword(editText_password,textView_password_error_msg);
        confirmPasswordValidated = userController.validatePassword(editText_confirmPassword, textView_passwords_error_msg);
        passwordsValidated = passwordValidated && confirmPasswordValidated;
        userController.checkIfPasswordsMatch(passwords, textView_passwords_error_msg,passwordsValidated);
        if(firstNameValidated && emailValidated && passwordValidated && confirmPasswordValidated ==true){
            userDetails = userController.getUserDetails(map);
            boolean accountCreated = userController.createUserAccount(editText_firstName.getText().toString(), editText_email.getText().toString());
            if(accountCreated == true){
                db.userDao().insertUserAccont(new User(0,editText_email.getText().toString(), editText_firstName.getText().toString()));
                Toast.makeText(getApplicationContext(), "You have successfully created an account, you can now proceed to login",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }
    }

    public void loginLinkTextClicked(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
