package com.ar.oe.classes;


import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;


@SuppressWarnings("rawtypes")
public class Post implements Serializable, Comparable {

	private static final long serialVersionUID = 5329308668228500983L;
	private long id;
	private String title;
	private String website;
	private String thumbnail;
	private String url;
	private String pubDate;
	private String author;
    private String language;
	
	public Post(){
		super();
		this.id = (long)0;
		this.title = "";
		this.website = "";
		this.thumbnail = "";
		this.url = "";
		this.pubDate = "";
        this.author = "";
        this.language = "";
	}
	
	public Post(Long id, String title, String website, String thumbnail, String url, String pubDate, String author, String language){
		this.id = id;
		this.title = title;
		this.website = website;
		this.thumbnail = thumbnail;
		this.url = url;
		this.pubDate = pubDate;
        this.author = author;
        this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getPubDate() {
		return pubDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Post createFromParcel(Parcel in) {
			return new Post(in); 
		}

		public Post[] newArray(int size) {
			return new Post[size];
		}
	};
	
	public Post(Parcel in){
		title = in.readString();
		website = in.readString();
		thumbnail = in.readString();
		url = in.readString();
		pubDate = in.readString();
        author = in.readString();
        language = in.readString();
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
