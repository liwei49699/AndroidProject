package com.liwei.composelib.utils

import kotlin.experimental.and

/**
 * @Description: desc
 * @Author: apple
 * @CreateDate: 2022/8/10 1:28 下午
 * @Version: 1.0
 */
object CRC16Utils {
    /**
     * crc16-ccitt-false加/解密（四字节）
     * @param bytes 字节数组
     * @return
     */
    fun crc16(bytes: ByteArray): Int {
        val len = bytes.size
        var crc = 0xFFFF
        for (j in 0 until len) {
            crc = crc ushr 8 or (crc shl 8) and 0xffff
            crc = crc xor ((bytes[j] and 0xff.toByte()).toInt()) // byte to int, trunc sign
            crc = crc xor (crc and 0xff shr 4)
            crc = crc xor (crc shl 12 and 0xffff)
            crc = crc xor (crc and 0xFF shl 5 and 0xffff)
        }
        crc = crc and 0xffff
        return crc
    }
}