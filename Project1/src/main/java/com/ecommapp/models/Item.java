package com.ecommapp.models;

import java.util.Objects;

import org.bson.types.ObjectId;

public class Item {
	
	public Item() {
	
	}
	public Item(String name, String value) {
		this.set_id(new ObjectId());
		this.setName(name);
		this.setValue(value);
	}
	private ObjectId id;
	private String name;
	private String value;
		
	public ObjectId getId() {
		return id;
	}
	public Item set_id(ObjectId id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Item setName(String name) {
		this.name = name;
		return this;
	}
	public String getValue() {
		return value;
	}
	public Item  setValue(String value) {
		this.value = value;
		return this;
	}
	
	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("name='").append(name).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Item item1 = (Item) o;
        return Objects.equals(id, item1.id) && Objects.equals(name, item1.name) && Objects.equals(value, item1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
