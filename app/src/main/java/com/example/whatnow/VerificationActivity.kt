package com.example.whatnow

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.whatnow.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    private lateinit var auth: FirebaseAuth
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var checkVerificationRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        // Resend verification email logic
        val resendButton = findViewById<Button>(R.id.btn_resend_email)
        resendButton.setOnClickListener {
            val user = Firebase.auth.currentUser
            user?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Verification email resent. Please check your inbox.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to resend verification email.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        startAutoCheck()
    }
    private fun startAutoCheck() {
        checkVerificationRunnable = object : Runnable {
            override fun run() {
                val user = auth.currentUser
                user?.reload()?.addOnCompleteListener { task ->
                    if (task.isSuccessful && user.isEmailVerified) {
                        Toast.makeText(this@VerificationActivity, "Email verified! Redirecting...", Toast.LENGTH_SHORT).show()
                        redirectToMainActivity()
                    } else {
                        // Re-run the check after 5 seconds
                        handler.postDelayed(this, 2000)
                    }
                }
            }
        }
        handler.postDelayed(checkVerificationRunnable, 5000) // First check after 5 seconds
    }
    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close VerificationActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(checkVerificationRunnable) // Stop the automatic check when the activity is closed
    }
}



