package com.liwei.androidproject

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * @Description: desc
 * @Author: apple
 * @CreateDate: 2022/7/15 11:49 上午
 * @Version: 1.0
 */
object Te {
    // 放大缩小图片
    fun scaleTo(bitmapOrg: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bitmapOrg.width
        val height = bitmapOrg.height
        if (width == 0 || height == 0) {
            return bitmapOrg
        }
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true)
    }
}