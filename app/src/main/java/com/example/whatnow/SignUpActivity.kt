package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.example.whatnow.databinding.ActivitySignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val sharedPref = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            // If the user is already logged in, redirect to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SignUpActivity
        }

//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//
//            Log.w("myApp", "$currentUser");
//            // If the user is not logged in, redirect to SignUpActivity
////            val intent = Intent(this, MainActivity::class.java)
////            startActivity(intent)
////            finish() // Close MainActivity so the user can't navigate back to it
//        }
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.signupButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

            // Validate fields
            if (TextUtils.isEmpty(email)) {
                binding.emailInput.error = "Email is required"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                binding.passwordInput.error = "Password is required"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                binding.confirmPasswordInput.error = "Confirm Password is required"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                binding.confirmPasswordInput.error = "Passwords do not match"
                return@setOnClickListener
            }
            if(password.length<8)
                Toast.makeText(this,"Password should be at least 8 characters", Toast.LENGTH_SHORT).show()


            signUpWithEmailPassword(email, password)
        }



        // Login link click listener to navigate to login page
        binding.loginLink.setOnClickListener {
           intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Navigate to LoginActivity

        }
    }

    // Function to handle Firebase sign up
    private fun signUpWithEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val sharedPref = getSharedPreferences("app_preferences", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putBoolean("is_logged_in", true)  // Save login state
                    editor.apply()  // Commit changes
                    verifyEmail()
                } else {
                    Toast.makeText(this, "Try again ,Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun verifyEmail(){
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"check your email", Toast.LENGTH_SHORT).show()
                    // Redirect to VerificationActivity after email verification
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
    }


}
