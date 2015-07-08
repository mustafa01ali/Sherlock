package org.gdghyderabad.sherlock.Binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.gdghyderabad.sherlock.BR;
import org.gdghyderabad.sherlock.model.VolumeInfo;

/**
 * Created by phanirajabhandari on 7/8/15.
 */
public class BookDetails extends BaseObservable{
    private VolumeInfo volumeInfo;
    private int index;

    @Bindable
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyPropertyChanged(BR.index);
    }

    @Bindable
    public VolumeInfo getVolumeInfo() {
        return volumeInfo;

    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
        notifyPropertyChanged(BR.volumeInfo);
    }
}
