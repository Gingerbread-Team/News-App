package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            // Validate fields
            if (TextUtils.isEmpty(email)) {
                binding.emailInput.error = "Email is required"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                binding.passwordInput.error = "Password is required"
                return@setOnClickListener
            }
            if (password.length < 8) {
                Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(email, password)
        }

        binding.signLink.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.forgot.setOnClickListener {
            val emailAddress = binding.emailInput.text.toString().trim()

            if (TextUtils.isEmpty(emailAddress)) {
                Toast.makeText(this, "Please enter your email first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.forgot.isEnabled = false // Disable button temporarily

            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    binding.forgot.isEnabled = true // Re-enable button after request

                    if (task.isSuccessful) {
                        AlertDialog.Builder(this)
                            .setTitle("Reset Password")
                            .setMessage("A password reset link has been sent to your email. Check your inbox and spam folder.")
                            .setPositiveButton("Go to Email") { _, _ ->
                                val intent = Intent(Intent.ACTION_MAIN)
                                intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                                startActivity(intent)
                            }
                            .setNegativeButton("OK", null)
                            .show()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(this@LoginActivity, "Logged in", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close LoginActivity
                    } else {
                        Toast.makeText(this@LoginActivity, "Check your email for verification!", Toast.LENGTH_SHORT).show()
                        auth.signOut() // Prevent login without verification
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
