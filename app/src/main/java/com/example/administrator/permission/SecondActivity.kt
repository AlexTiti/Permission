package com.example.administrator.permission

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        EventBus.getDefault().register(this)
        btnSecond.setOnClickListener {
//            val event = EventBus.getDefault().getStickyEvent(UserEvent::class.java)
//            if (event != null){
                EventBus.getDefault().removeStickyEvent(UserEvent::class.java)
                EventBus.getDefault().removeAllStickyEvents()
//            }
            startActivity(Intent(this, ThirdActivity::class.java)) }
    }

    @Subscribe(sticky = true)
    public fun getEvent(userEvent: UserEvent) {
        Toast.makeText(this, "${userEvent.id}  ${userEvent.name}  ${userEvent.age}", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
