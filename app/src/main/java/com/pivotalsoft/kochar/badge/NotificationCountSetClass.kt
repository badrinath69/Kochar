package com.pivotalsoft.kochar.badge

import android.app.Activity
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.view.MenuItem
import com.pivotalsoft.kochar.R

class NotificationCountSetClass : Activity() {
    companion object {
        private var icon: LayerDrawable? = null

        fun setAddToCart(context: Context, item: MenuItem, numMessages: Int) {
            icon = item.icon as LayerDrawable
            SetNotificationCount.setBadgeCount(context, icon!!, NotificationCountSetClass.setNotifyCount(numMessages))

        }

        fun setNotifyCount(numMessages: Int): Int {
            return numMessages

        }
    }


}//constructor


object SetNotificationCount {
    fun setBadgeCount(context: Context, icon: LayerDrawable, count: Int) {

        val badge: BadgeDrawable

        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_badge)
        if (reuse != null && reuse is BadgeDrawable) {
            badge = reuse
        } else {
            badge = BadgeDrawable(context)
        }

        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_badge, badge)
    }
}