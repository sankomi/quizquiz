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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String key;
	private boolean verified;

	@Builder
	public User(String username, String key) {
		this.username = username;
		this.key = key;
		this.verified = false;
	}

	public void verify() {
		this.verified = true;
	}

}
