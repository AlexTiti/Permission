package com.example.administrator.permission

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        val rxPermissions = RxPermissions(this)
        rxPermissions.setLogging(true)
        button.setOnClickListener(View.OnClickListener {
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(Consumer<Boolean> { aBoolean ->
                        if (aBoolean!!) {
                            Toast.makeText(this@MainActivity, "授权成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "授权失败", Toast.LENGTH_SHORT).show()
                        }
                    })
        })
        button2.setOnClickListener(View.OnClickListener {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE)
                    .subscribe(Consumer<Boolean> { aBoolean ->
                        if (aBoolean!!) {
                            Toast.makeText(this@MainActivity, "授权成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "授权失败", Toast.LENGTH_SHORT).show()
                        }
                    })
        })
        button3.setOnClickListener(View.OnClickListener {
            rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR)
                    .subscribe(Consumer<Permission> { permission ->
                        if (permission.granted) {
                            Toast.makeText(this@MainActivity, "授权成功" + permission.name, Toast.LENGTH_SHORT).show()
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Toast.makeText(this@MainActivity, "需要授权才能执行" + permission.name, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "授权失败" + permission.name, Toast.LENGTH_SHORT).show()
                        }
                    })
        })
        button4.setOnClickListener(View.OnClickListener {
            EventBus.getDefault().postSticky(UserEvent(1, "DDDDD", 30))
            startActivity(Intent(this, SecondActivity::class.java))
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    public fun toast(userEvent: UserEvent) {
        Toast.makeText(this, "${userEvent.id}  ${userEvent.name}  ${userEvent.age}", Toast.LENGTH_SHORT).show()
    }
}
