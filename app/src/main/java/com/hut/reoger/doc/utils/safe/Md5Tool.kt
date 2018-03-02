package com.hut.reoger.doc.utils.safe

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by reoger on 2018/3/2.
 */

object Md5Tool {

    private fun bytesToHexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }

    @JvmStatic
    fun hashKey(key: String): String {
        var hashKey: String
        return try {
            val mDigest = MessageDigest.getInstance("MD5")
            mDigest.update(key.toByteArray())
            hashKey = bytesToHexString(mDigest.digest())
            hashKey
        } catch (e: NoSuchAlgorithmException) {
            key.hashCode().toString()
            ""
        }

    }

    /**
     * 这个也是md5加密，应该两个都可以用
     */
    @JvmStatic
    fun encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest:ByteArray = instance.digest(text.toByteArray())
            var sb : StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i :Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }


}