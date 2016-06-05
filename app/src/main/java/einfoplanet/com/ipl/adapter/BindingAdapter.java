package einfoplanet.com.ipl.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Binding adapter for the bitmap images of team logo and the player images.
 */

public class BindingAdapter {
    @android.databinding.BindingAdapter({"bind:imageBitmap"})
    public static void loadImage(ImageView view, Bitmap imageBitmap) {
        view.setImageBitmap(imageBitmap);
    }

    @android.databinding.BindingAdapter({"bind:imageBitmap"})
    public static void loadPlayerImage(ImageView view, Bitmap imageBitmap) {
        view.setImageBitmap(imageBitmap);
    }
}
