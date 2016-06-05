package einfoplanet.com.ipl.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;

import einfoplanet.com.ipl.BR;

/**
 * Created by eshvar289 on 5/6/16.
 */

public class PlayerViewModel extends BaseObservable{
    //--Declaration of attributes required by the View
    public String mPlayerName;
    public String mPlayerRole;
    public String mPlayerBattingStyle;
    public String mPlayerBowlingStyle;
    public String mPlayerDOB;
    public String mPlayerNationality;
    public Bitmap mPlayerImageBitmap;

    @Bindable
    public String getmPlayerName() {
        return mPlayerName;
    }

    public void setmPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
        notifyPropertyChanged(BR.mPlayerName);
    }
    @Bindable
    public String getmPlayerRole() {
        return mPlayerRole;
    }

    public void setmPlayerRole(String mPlayerRole) {
        this.mPlayerRole = mPlayerRole;
        notifyPropertyChanged(BR.mPlayerRole);
    }
    @Bindable
    public String getmPlayerBattingStyle() {
        return mPlayerBattingStyle;
    }

    public void setmPlayerBattingStyle(String mPlayerBattingStyle) {
        this.mPlayerBattingStyle = mPlayerBattingStyle;
        notifyPropertyChanged(BR.mPlayerBattingStyle);
    }
    @Bindable
    public String getmPlayerBowlingStyle() {
        return mPlayerBowlingStyle;
    }

    public void setmPlayerBowlingStyle(String mPlayerBowlingStyle) {
        this.mPlayerBowlingStyle = mPlayerBowlingStyle;
        notifyPropertyChanged(BR.mPlayerBowlingStyle);
    }
    @Bindable
    public String getmPlayerDOB() {
        return mPlayerDOB;
    }

    public void setmPlayerDOB(String mPlayerDOB) {
        this.mPlayerDOB = mPlayerDOB;
        notifyPropertyChanged(BR.mPlayerDOB);
    }
    @Bindable
    public String getmPlayerNationality() {
        return mPlayerNationality;

    }

    public void setmPlayerNationality(String mPlayerNationality) {
        this.mPlayerNationality = mPlayerNationality;
        notifyPropertyChanged(BR.mPlayerNationality);
    }
    @Bindable
    public Bitmap getmPlayerImageBitmap() {
        return mPlayerImageBitmap;
    }

    public void setmPlayerImageBitmap(Bitmap mPlayerImageBitmap) {
        this.mPlayerImageBitmap = mPlayerImageBitmap;
        notifyPropertyChanged(BR.mPlayerImageBitmap);
    }
}
