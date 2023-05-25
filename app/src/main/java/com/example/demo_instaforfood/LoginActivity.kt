package com.example.demo_instaforfood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.engine.Resource
import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import com.example.demo_instaforfood.ViewModels.LoginViewModel
import com.example.demo_instaforfood.ViewModels.ReviewsViewModel
import com.example.demo_instaforfood.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val isLogin = sharedPreferencesHelper.getLoginStatus()
        if (isLogin) {
            moveToHomeScreen()
        } else {

            loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

            binding.btnLogin.setOnClickListener {

                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                if (email != "" && password != "") {
                    loginViewModel.login(LoginRequest(email, password))
                } else {
                    Toast.makeText(this@LoginActivity, "Fields cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
                loginViewModel.loginResponse.observe(this, Observer {
                    if (it.isSuccessful) {
                        sharedPreferencesHelper.setloginStatus(true)
                        moveToHomeScreen()
                    } else {
                        Toast.makeText(this@LoginActivity, it.message(), Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }

    private fun moveToHomeScreen() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

}