package einfoplanet.com.ipl.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/*
*   PictureUtil is an utility class which is used for two purposes
*   1) First to download the image and convert it into the Bitmap and store it into the file storage.
*   2) Second to load the required image from the file storage and convert it into the Bitmap.
*/
public class PictureUtil {
    private static String TAG = "PictureUtil";

    //--Getting the root of the FileDirectory for to save the data
    private static File getFilePath(String filename, String folder) {
        File filePath;
        if (hasSDCard()) {
            filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + folder + filename);
        } else {
            filePath = Environment.getDataDirectory();
        }
        return filePath;
    }

    //--Getting the Bitmap of the image from the internal storage
    public static Bitmap loadFileFromStorage(String fileName, String folder) {
        File filePath = getFilePath(fileName, folder);
        return loadFromFile(filePath.getAbsolutePath());
    }

    private static Bitmap loadFromFile(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists())
                return null;

            Bitmap bitmap = BitmapFactory.decodeFile(filename);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    //--Convert imageUrl to Image
    public static Bitmap urlToImageDownload(String imgUrl, String filename, String folder) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            storeImageInFile(bitmap, filename, folder);
            Log.d(TAG, "enter in the urlToImageDownload()");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //--Method to store image into the sdcard
    private static void storeImageInFile(Bitmap bitmap, String ImageName, String foldername) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            File directory = new File(root + foldername);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            OutputStream fOut = null;

            File myPath = new File(directory, ImageName);
            if (myPath.exists())
                myPath.delete();
            myPath.createNewFile();
            fOut = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Convert imageUrl to Bitmap
    public static Bitmap urlToBitmap(String imgUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //--method to check if there is External SD card is available or not
    private static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }
}
