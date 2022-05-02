package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Member extends BaseEntity {

	@Id
	private Long id;

	@Column(name = "username")
	private String name;

	private Integer age;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
