package com.liwei.androidproject

import android.graphics.RectF

class Mark(var postion: Int) : RectF() {
    override fun toString(): String {
        return "Mark [postion=$postion, left=$left, top=$top, right=$right, bottom=$bottom]"
    }
}