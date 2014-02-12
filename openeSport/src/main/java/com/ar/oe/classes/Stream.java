package com.ar.oe.classes;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class Stream implements Serializable, Comparable {

	private static final long serialVersionUID = 5329308668228500983L;
	private long id;
	private String name;
	private String img;
	private String status;
	private String url;
    private String viewers;
    private String game;

	public Stream(){
		super();
		this.id = (long)0;
		this.name = "";
		this.img = "";
		this.status = "";
		this.url = "";
        this.viewers = "";
        this.game = "";
	}

	public Stream(Long id, String name, String img, String status, String url, String viewers, String game){
		this.id = id;
		this.name = name;
		this.img = img;
		this.status = status;
		this.url = url;
		this.viewers = viewers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public String getViewers() {
        return viewers;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGame() {
        return game;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Stream createFromParcel(Parcel in) {
			return new Stream(in);
		}

		public Stream[] newArray(int size) {
			return new Stream[size];
		}
	};

	public Stream(Parcel in){
        name = in.readString();
		img = in.readString();
        status = in.readString();
		url = in.readString();
        viewers = in.readString();
        game = in.readString();
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
