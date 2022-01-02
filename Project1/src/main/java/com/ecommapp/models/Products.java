package com.ecommapp.models;

import java.util.Date;
import java.util.Objects;

import org.bson.types.ObjectId;



public class Products {

	private ObjectId id;
	private long pid;
	private String name;
	private long price;
	private String description;
	private String url;
	
	public Products() {
		
	}
	
	public Products(String name, long price, String description, String url) {
		this.id = new ObjectId();
		this.pid= new Date().getTime();
		this.name = name;
		this.price = price;
		this.description = description;
		this.url = url;
	}
	public ObjectId getId() {
		return id;
	}


	public Products setId(ObjectId id) {
		this.id = id;
		return this;
	}
	
	public long getPid() {
		return pid;
	}
	public Products setPid(long pid) {
		this.pid = pid;
		return this;
	}

	public String getName() {
		return name;
	}


	public Products setName(String name) {
		this.name = name;
		return this;
	}


	public long getPrice() {
		return price;
	}


	public Products setPrice(long price) {
		this.price = price;
		return this;
	}



	public String getDescription() {
		return description;
	}


	public Products setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append(", pid='").append(pid).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", url=").append(url);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Products prod1 = (Products) o;
        return Objects.equals(id, prod1.id) && Objects.equals(pid, prod1.pid) && Objects.equals(name, prod1.name) && Objects.equals(price, prod1.price) 
        		&& Objects.equals(description, prod1.description) && Objects.equals(description, prod1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, url );
    }

	
}
