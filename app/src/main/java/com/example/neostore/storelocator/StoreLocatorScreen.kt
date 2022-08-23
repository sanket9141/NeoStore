package com.example.neostore.storelocator

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.neostore.R
//import com.example.neostore.fragment.StoreLocatorFragment

class StoreLocatorScreen : AppCompatActivity() {
    val LocationCode = 102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_locator_screen)

/*
        checkForPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION,"Location",LocationCode)
        val fragment = StoreLocatorFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.LocatorScreen,fragment)
            .commit()
    }
    private fun checkForPermission(permission:String,name:String,requestCode:Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED->{
                    Toast.makeText(applicationContext,"$name permission granted", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission)-> showDialog(permission,name,requestCode)
                else-> ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"$name permission denied",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"$name permission granted",Toast.LENGTH_SHORT).show()
            }
        }
        when(requestCode){
            LocationCode->innerCheck("Location")
        }
    }
    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("permission to access your $name is required to used this app")
            setTitle("Permission required")
            setPositiveButton("ok") {dialog,which->
                ActivityCompat.requestPermissions(this@StoreLocatorScreen, arrayOf(permission),requestCode)
            }
        }
        val dialog= builder.create()
        dialog.show()

 */
    }
}