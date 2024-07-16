import {BrowserQRCodeReader} from "@zxing/browser";
import {authenticator} from "otplib";

const username = `cypress.${Cypress._.random(0, Number.MAX_SAFE_INTEGER)}`;
let secret;
const notUsername = `cypress.${Cypress._.random(0, Number.MAX_SAFE_INTEGER)}`;
const notSecret = "notsecret";

Cypress.session.clearAllSavedSessions();

describe("index", () => {
	beforeEach(() => {
		cy.visit("http://localhost:8080");
	});

	it("should load", () => {
	});

	it("should link to create", () => {
		cy.get(":nth-child(2) > a").click();
		cy.url().should("include", "/create");
	});

	it("should link to login", () => {
		cy.get(":nth-child(3) > a").click();
		cy.url().should("include", "/login");
	});
});

describe("create", () => {
	beforeEach(() => {
		cy.visit("http://localhost:8080/create");
	});

	describe("first time", () => {
		it("should generate qrcode and redirect to /list on verify", () => {
			cy.get("#username").type(username);
			cy.get("#create > :nth-child(2) > input").click();

			cy.get("#image")
				.should("have.attr", "src")
				.should("contain", "data:image/png;base64,");

			cy.get("#image")
				.then(async img => {
					const image = new Image();
					image.width = img[0].width;
					image.height = img[0].height;
					image.src = img[0].src;

					const reader = new BrowserQRCodeReader();
					const result = await reader.decodeFromImageElement(image)
					const string = result.text;

					const start = string.indexOf("secret=") + 7;
					const end = string.lastIndexOf("&issuer=");
					secret = string.substring(start, end);
					const password = authenticator.generate(secret);

					cy.get("#password").type(password);
					cy.get(":nth-child(3) > input").click();
					cy.url().should("include", "/list");
				});
		});
	});

	describe("second time", () => {
		it("should say 'username exists'", () => {
			cy.get("#username").type(username);
			cy.get("#create > :nth-child(2) > input").click();

			cy.get("#message").should("have.text", "username exists");
		});
	});

	after(() => cy.wait(30000));
});

function login() {
	cy.visit("http://localhost:8080/login");

	//login
	cy.get("#username").type(username);
	cy.get("#password").type(authenticator.generate(secret));
	cy.get(":nth-child(3) > input").click();

	cy.url().should("contain", "/list");
}

describe("login", () => {
	beforeEach(() => {
		cy.visit("http://localhost:8080/login");
	});

	describe("username does not exist", () => {
		it("should say 'not found'", () => {
			cy.get("#username").type(notUsername);
			cy.get("#password").type(authenticator.generate(notSecret));
			cy.get(":nth-child(3) > input").click();

			cy.get("#message").should("have.text", "not found");
		});
	});

	describe("password is incorrect", () => {
		it("should say 'password incorrect'", () => {
			cy.get("#username").type(username);
			cy.get("#password").type(authenticator.generate(notSecret));
			cy.get(":nth-child(3) > input").click();

			cy.get("#message").should("have.text", "password incorrect");
		});
	});

	describe("correct username and password", () => {
		it("should redirect to /list", () => {
			cy.session("login", login);
		});
	});
});

describe("list", () => {
	beforeEach(() => {
		cy.session("login", login);

		cy.intercept("GET", "/quiz").as("list");
		cy.visit("http://localhost:8080/list");
		cy.wait("@list");
	});

	describe("initially", () => {
		it("should not have any quizzes", () => {
			cy.get("#list")
				.children()
				.should("have.length", 0);
		});
	});

	describe("add quiz", () => {
		it("should add list item with 'untitled quiz' and edit/play buttons", () => {
			cy.get("#add").click();

			cy.get("#list")
				.children()
				.should("have.length", 1);
			cy.get("#list")
				.children("li:first")
				.children("span")
				.should("have.text", "untitled quiz");
			cy.get("#list")
				.children("li:first")
				.children("a:nth-of-type(1)")
				.should("have.text", "edit")
				.should("have.attr", "href")
				.and("include", "/edit/");
			cy.get("#list")
				.children("li:first")
				.children("a:nth-of-type(2)")
				.should("have.text", "play")
				.should("have.attr", "href")
				.and("include", "/play/");
		});
	});

	describe("edit quiz", () => {
		it("should go to quiz edit page", () => {
			cy.get("#list")
				.children("li:first")
				.children("a:contains('edit')")
				.click();

			cy.url().should("include", "/edit");
		});
	});

	describe("play quiz", () => {
		it("should go to quiz play page", () => {
			cy.get("#list")
				.children("li:first")
				.children("a:contains('play')")
				.click();

			cy.url().should("include", "/play");
		});
	});
});

const quizTitle = "this is a test quiz";

describe("edit", () => {
	beforeEach(() => {
		cy.session("login", login);

		cy.intercept("GET", "/quiz").as("list");
		cy.visit("http://localhost:8080/list");
		cy.wait("@list");

		//edit
		cy.get("#list")
			.children("li:first")
			.children("a:contains('edit')")
			.click();
	});

	describe("change title", () => {
		it("should change title", () => {
			cy.window().then(w => cy.stub(w, "prompt").returns(quizTitle));

			cy.get("#title")
				.children("button:contains('edit')")
				.click();

			cy.get("#title")
				.children("span")
				.should("have.text", quizTitle);
		});
	});

	describe("toggle open/close", () => {
		it("should change 'closed' to 'opened'", () => {
			cy.get("#title")
				.children("button:contains('closed')")
				.click();

			cy.get("#title")
				.children("button:contains('opened')")
				.should("exist");
		});

		it("should change 'opened' to 'closed'", () => {
			cy.get("#title")
				.children("button:contains('opened')")
				.click();

			cy.get("#title")
				.children("button:contains('closed')")
				.should("exist");
		});

		after(() => {
			//change to open
			cy.get("#title")
				.children("button:contains('closed')")
				.click();
		});
	});

	describe("toggle question shuffle", () => {
		it("should change 'ordered' to 'shuffled'", () => {
			cy.get("#title")
				.children("button:contains('questions ordered')")
				.click();

			cy.get("#title")
				.children("button:contains('questions shuffled')")
				.should("exist");
		});

		it("should change 'shuffled' to 'ordered'", () => {
			cy.get("#title")
				.children("button:contains('questions shuffled')")
				.click();

			cy.get("#title")
				.children("button:contains('questions ordered')")
				.should("exist");
		});
	});

	describe("toggle answers shuffle", () => {
		it("should change 'ordered' to 'shuffled'", () => {
			cy.get("#title")
				.children("button:contains('answers ordered')")
				.click();

			cy.get("#title")
				.children("button:contains('answers shuffled')")
				.should("exist");
		});

		it("should change 'shuffled' to 'ordered'", () => {
			cy.get("#title")
				.children("button:contains('answers shuffled')")
				.click();

			cy.get("#title")
				.children("button:contains('answers ordered')")
				.should("exist");
		});
	});
});
