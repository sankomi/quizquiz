package sanko.quiz.user;

import jakarta.persistence.*; //Entity, Table, Id, Column, GeneratedValue, GenerationType

import lombok.*; //NoArgsConstructor, Getter, Builder
import lombok.experimental.Accessors;

@NoArgsConstructor
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
