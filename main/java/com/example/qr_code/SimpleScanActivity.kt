package com.example.qr_code

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.EncodeHintType
import com.google.zxing.LuminanceSource
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.oned.EAN13Writer
import com.google.zxing.qrcode.QRCodeReader
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Hashtable

class SimpleScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_scan)

        findViewById<Button>(R.id.btnGenerate2).setOnClickListener {
            val text = findViewById<EditText>(R.id.text_for_qr2).text
            val bitmap = generateCode(text.toString(), BarcodeFormat.QR_CODE)
            findViewById<ImageView>(R.id.QRimage2).setImageBitmap(bitmap)
        }
        findViewById<Button>(R.id.codeEAN13_2).setOnClickListener {
            val bitmap = findViewById<ImageView>(R.id.QRimage2).drawable.toBitmap()
            val text = decoderQR(bitmap)
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

    fun decoderQR(bitmap: Bitmap): String {
        val reader = QRCodeReader()
        return reader.decode(BitmapToBinaryBitmap(bitmap)).text.toString()
    }

    fun BitmapToBinaryBitmap(src: Bitmap): BinaryBitmap {
        val intArray = IntArray(src.width * src.height)
        src.getPixels(intArray, 0, src.width, 0, 0, src.width, src.height)

        val source: LuminanceSource = RGBLuminanceSource(src.width, src.height, intArray)
        return BinaryBitmap(HybridBinarizer(source))
    }

    fun generateCode(text: String, codeType: BarcodeFormat): Bitmap {
        var writer: Writer? = null
        lateinit var bitMatrix: BitMatrix

        if (codeType == BarcodeFormat.EAN_13) {
            writer = EAN13Writer()
        } else if (codeType == BarcodeFormat.QR_CODE) {
            writer = QRCodeWriter()
        }
        //val writer = QRCodeWriter()
        var bitmap: Bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.RGB_565)
        try {
            if (codeType == BarcodeFormat.EAN_13) {
                bitMatrix = writer!!.encode(text, codeType, 1024, 1024)
            } else if (codeType == BarcodeFormat.QR_CODE) {
                val hintMap = Hashtable<EncodeHintType, String>()
                hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8")
                bitMatrix = writer!!.encode(text, codeType, 1024, 1024, hintMap)
            }
            //val bitMatrix: BitMatrix = writer!!.encode(text, BarcodeFormat.QR_CODE, 1024, 1024)
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