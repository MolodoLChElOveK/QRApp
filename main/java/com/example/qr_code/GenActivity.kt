package com.example.qr_code

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.EAN13Writer
import com.google.zxing.qrcode.QRCodeWriter

class GenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gen)

        findViewById<Button>(R.id.btnGenerate).setOnClickListener {
            val text = findViewById<EditText>(R.id.text_for_qr).text
            val bitmap = generateCode(text.toString(), BarcodeFormat.QR_CODE)
            findViewById<ImageView>(R.id.QRimage).setImageBitmap(bitmap)
        }
        findViewById<Button>(R.id.codeEAN13).setOnClickListener {
            val text = findViewById<EditText>(R.id.text_for_qr).text
            if (text.length != 12) {
                Toast.makeText(this, "EAN 13 length must be 12 or 13", Toast.LENGTH_LONG).show()
            } else {
                val bitmap = generateCode(text.toString(), BarcodeFormat.EAN_13)
                findViewById<ImageView>(R.id.QRimage).setImageBitmap(bitmap)
            }
        }
    }

    fun generateCode(text: String, codeType: BarcodeFormat): Bitmap {
        var writer: Writer? = null
        if (codeType == BarcodeFormat.EAN_13) {
            writer = EAN13Writer()
        } else if (codeType == BarcodeFormat.QR_CODE) {
            writer = QRCodeWriter()
        }

        var bitmap: Bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.RGB_565)
        try {
            val bitMatrix: BitMatrix = writer!!.encode(text, codeType, 1024, 1024)
            val width = bitMatrix.width
            val height = bitMatrix.height
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }
}