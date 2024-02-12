package com.example.registrationsplashscreen;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextDOB, editTextEmail, editTextPassword;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextFirstName = findViewById(R.id.FirstName);
        editTextLastName = findViewById(R.id.LastName);
        editTextDOB = findViewById(R.id.DOB);
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);

        initDatePicker();

        editTextDOB.setFocusable(false);
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    private void initDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String date = dayOfMonth + "/" + monthOfYear + "/" + year;
                editTextDOB.setText(date);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
    }

    public void onRegisterClick(View view) {
        if (validateInputs()) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            // Here, you would handle the registration logic, like sending data to your server.
            finish();
        }
    }

    private boolean validateInputs() {
        if (!validateName(editTextFirstName.getText().toString(), "First Name", 3, 30)) {
            return false;
        }

        if (!validateName(editTextLastName.getText().toString(), "Last Name", 3, 30)) {
            return false;
        }

        if (editTextDOB.getText().toString().trim().isEmpty()) {
            editTextDOB.setError("Date of Birth is required");
            return false;
        }

        if (!isValidEmail(editTextEmail.getText().toString())) {
            editTextEmail.setError("Invalid Email Address");
            return false;
        }

        if (editTextPassword.getText().toString().trim().isEmpty() || editTextPassword.getText().toString().trim().length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }

    private boolean validateName(String name, String fieldName, int minLen, int maxLen) {
        if (name.trim().isEmpty() || name.trim().length() < minLen || name.trim().length() > maxLen) {
            String errorMsg = fieldName + " must be between " + minLen + " and " + maxLen + " characters";
            if ("First Name".equals(fieldName)) {
                editTextFirstName.setError(errorMsg);
            } else {
                editTextLastName.setError(errorMsg);
            }
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}").matcher(email).matches();
    }
}
