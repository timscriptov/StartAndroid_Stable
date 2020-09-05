package com.startandroid.entity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import com.startandroid.R
import com.startandroid.interfaces.OfflineListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zeroturnaround.zip.ZipUtil
import java.io.*
import java.net.URL
import kotlin.coroutines.CoroutineContext

class OfflineCoroutine(@field:SuppressLint("StaticFieldLeak") private val mListener: OfflineListener, @field:SuppressLint("StaticFieldLeak") private val context: Context) : CoroutineScope {
    private var progressDialog: ProgressDialog? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun execute() = launch {
        onPreExecute()
        val result = doInBackground()
        onPostExecute(result)
    }

    private fun onPreExecute() {
        mListener.onProcess()
        progressDialog = ProgressDialog(context)
        progressDialog!!.setTitle(context.getString(R.string.loading))
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.cancel)) { dialogInterface: DialogInterface?, i: Int -> onCancelled() }
        progressDialog!!.show()
    }

    private suspend fun doInBackground(): Boolean = withContext(Dispatchers.IO) {
        var count: Int
        val data = ByteArray(1024)
        return@withContext try {
            val url = URL("https://timscriptov.github.io/lessons/startandroid.zip")
            val connection = url.openConnection()
            val offline = File(context.filesDir, "offline.zip")
            val resourcesDir = File(context.filesDir, "resources")
            progressDialog!!.max = connection.contentLength
            val inputStream: InputStream = BufferedInputStream(connection.getInputStream())
            val outputStream: OutputStream = FileOutputStream(offline)
            while (inputStream.read(data).also { count = it } != -1) {
                progressDialog!!.incrementProgressBy(count)

                outputStream.write(data, 0, count)
            }
            inputStream.close()
            outputStream.flush()
            ZipUtil.unpack(offline, resourcesDir)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun onPostExecute(bool: Boolean) {
        if (bool) {
            mListener.onCompleted()
        } else {
            mListener.onFailed()
        }
        progressDialog!!.dismiss()
    }

    private fun onCancelled() {
        mListener.onCanceled()
        progressDialog!!.dismiss()
    }
}