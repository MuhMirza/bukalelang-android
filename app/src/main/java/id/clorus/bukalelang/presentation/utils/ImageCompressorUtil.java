package id.clorus.bukalelang.presentation.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;


/**
 * Created by mirza on 08/04/17.
 */

public class ImageCompressorUtil {

    Context mContext;
    String imagePath;
    Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public ImageCompressorUtil(Context context, String imagePath){
        this.mContext = context;
        this.imagePath = imagePath;
        this.callback = null;

        new ImageCompressionAsyncTask(mContext).execute(imagePath);
    }



    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

        public ImageCompressionAsyncTask(Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            String filePath = SiliCompressor.with(mContext).compress(params[0]);
            return filePath;

        }

        @Override
        protected void onPostExecute(String s) {

            File imageFile = new File(s);

            if (callback != null){
                callback.onFinishCompress(Uri.fromFile(imageFile));
            }


        }
    }


    public interface Callback{

        void onFinishCompress(Uri imageUri);

    }
}
