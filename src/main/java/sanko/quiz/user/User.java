package sanko.quiz.user;

import jakarta.persistence.*; //Entity, Table, Id, Column, GeneratedValue, GenerationType

import lombok.*; //Builder, Getter
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String username;
	private String password;

	@Builder
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
