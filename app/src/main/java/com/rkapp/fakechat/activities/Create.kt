package com.rkapp.fakechat.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rkapp.fakechat.databinding.ActivityCreateBinding
import com.rkapp.fakechat.viewmodels.CreateViewModel

class Create : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private var picture: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val register = registerForActivityResult(StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {

                picture = it.data?.data

                Glide.with(this).load(picture).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.profile)
            }
        }

        binding.profile.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {

                type = "image/*"
            }

            register.launch(intent)
        }

        binding.create.setOnClickListener {

            if ( binding.profile.drawable != null ) {

                if ( binding.createId.text.isNotEmpty ( ) && binding.createEmail.text.isNotEmpty() && binding.createPassword.text.isNotEmpty()) {

                    val vm = ViewModelProvider (this ) [ CreateViewModel::class.java ]

                    vm.createUser ( binding.createId.text.toString ( ) , binding.createEmail.text.toString ( ) , binding.createPassword.text.toString ( ) , picture!! )

                    vm.result.observe ( this@Create ) {

                        if ( it.equals ( true ) ) {

                            startActivity ( Intent (this@Create , Main::class.java ) )

                            finish ( )

                        } else {

                            toast ("Kullanıcı Tanımlama Geçersiz" )
                        }
                    }

                } else {

                    toast ("Bos alanları doldurun" )
                }

            } else {

                toast ("Bir resim belirtin" )
            }
        }
    }

    private fun toast ( text : String ) {

        Toast.makeText (this , text , Toast.LENGTH_SHORT ).show ( )
    }
}

