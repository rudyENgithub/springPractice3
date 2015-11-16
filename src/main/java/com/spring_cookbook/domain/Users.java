package com.spring_cookbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class Users {
    

          @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "auto_gen")
  @SequenceGenerator(name = "auto_gen", sequenceName = "users_seq") 
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	private Integer age;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

}

