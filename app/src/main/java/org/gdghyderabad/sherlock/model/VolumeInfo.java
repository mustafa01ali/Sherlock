package org.gdghyderabad.sherlock.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mustafa Ali on 11/03/15.
 */
public class VolumeInfo implements Serializable {
    public String title;
    public String subtitle;
    public List<String> authors;
    public String publisher;
    public String description;
    public ImageLinks imageLinks;
}