package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koobookandroidapp.R;

import java.util.ArrayList;

import controllers.UserController;
import extras.JavaMail;

public class ResetPasswordActivity extends AppCompatActivity {
    TextView textview_passwords_error_msg;
    TextView textview_password_error_msg;
    EditText edittext_password;
    EditText edittext_confirmPassword;
    boolean passwordValidated;
    boolean confirmPasswordValidated;
    boolean passwordsValidated;
    boolean passwordResetted;
    ArrayList<EditText> passwords;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        textview_passwords_error_msg = findViewById(R.id.textview_passwords_error_msg);
        textview_password_error_msg = findViewById(R.id.textview_password_error_msg);
        edittext_password = findViewById(R.id.edittext_password);
        edittext_confirmPassword = findViewById(R.id.edittext_confirmpassword);
        passwords = new ArrayList<>();
    }

    //Validate that both fields are non empty. If that passes then check if they match then encrpyted the new password
    // and update the relevent user record in the database to include this new password
    public void resetButtonClicked(View v){
      UserController userController = new UserController();
      passwordValidated = userController.checkEditTextFieldNonEmpty(edittext_password, textview_password_error_msg, false);
      confirmPasswordValidated = userController.checkEditTextFieldNonEmpty(edittext_confirmPassword, textview_passwords_error_msg, false);

      if(passwordValidated && confirmPasswordValidated){
          passwords.add(edittext_password);
          passwords.add(edittext_confirmPassword);
          passwordsValidated = userController.checkIfPasswordsMatch(passwords, textview_passwords_error_msg,true);

          if(passwordsValidated == true){
              String newPassword = edittext_password.getText().toString();
              user_id = userController.getUserIdFromSharedPreferneces(this);
              passwordResetted = userController.resetPassword(user_id, newPassword);

              if(passwordResetted == true){
                  Toast.makeText(this, "Password successfully reset", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(this, LoginActivity.class);
                  startActivity(intent);
                  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
              }
          }
      }
    }
}
