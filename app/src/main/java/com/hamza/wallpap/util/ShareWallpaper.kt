package com.hamza.wallpap.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

fun shareWallpaper(context: Context, image: Bitmap?, shareWithWhatsAppOnly: Boolean, saveToDrive: Boolean) {
    val intent = Intent(Intent.ACTION_SEND).setType("image/*")
    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        image,
        "${System.currentTimeMillis()}",
        null
    )

    if (path != null) {
        if (shareWithWhatsAppOnly) {
            val uri = Uri.parse(path)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.setPackage("com.whatsapp")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Download WallPap for more exciting WallPapers!"
            )
            context.startActivity(intent)
        }
        else if (saveToDrive){
            val uri = Uri.parse(path)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.setPackage("com.google.android.apps.docs")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Download WallPap for more exciting WallPapers!"
            )
            context.startActivity(intent)
        }
        else {
            val uri = Uri.parse(path)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Download WallPap for more exciting WallPapers!"
            )
            context.startActivity(intent)
        }
    }
}