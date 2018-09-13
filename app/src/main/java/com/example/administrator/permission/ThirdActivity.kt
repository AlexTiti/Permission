package com.example.administrator.permission

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        EventBus.getDefault().register(this)

    }

    @Subscribe(sticky = true)
    public fun getEvent(userEvent: UserEvent) {
        Toast.makeText(this, "${userEvent.id}  ${userEvent.name}  ${userEvent.age}", Toast.LENGTH_SHORT).show()
        EventBus.getDefault().removeStickyEvent(UserEvent::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
