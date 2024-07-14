import {BrowserQRCodeReader} from "@zxing/browser";
import {authenticator} from "otplib";

let username = `cypress.${Cypress._.random(0, Number.MAX_SAFE_INTEGER)}`;
let secret;
let notUsername = `cypress.${Cypress._.random(0, Number.MAX_SAFE_INTEGER)}`;
let notSecret = "notsecret";

describe("index", () => {
	it("should load", () => {
		cy.visit("http://localhost:8080");
	});

	it("should link to create", () => {
		cy.visit("http://localhost:8080");
		cy.get(":nth-child(2) > a").click();
		cy.location("pathname").should("include", "/create");
	});

	it("should link to login", () => {
		cy.visit("http://localhost:8080");
		cy.get(":nth-child(3) > a").click();
		cy.location("pathname").should("include", "/login");
	});
});

describe("create", () => {
	describe("first time", () => {
		it("should generate qrcode and redirect to /list on verify", () => {
			cy.visit("http://localhost:8080/create");

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
					cy.location("pathname").should("include", "/list");
				});
		});
	});

	describe("second time", () => {
		it("should say 'username exists'", () => {
			cy.visit("http://localhost:8080/create");

			cy.get("#username").type(username);
			cy.get("#create > :nth-child(2) > input").click();

			cy.get("#message").should("have.text", "username exists");
		});
	});
});

describe("login", () => {
	describe("username does not exist", () => {
		it("should say 'not found'", () => {
			cy.visit("http://localhost:8080/login");

			cy.get("#username").type(notUsername);
			cy.get("#password").type(authenticator.generate(notSecret));
			cy.get(":nth-child(3) > input").click();

			cy.get("#message").should("have.text", "not found");
		});
	});

	describe("password is incorrect", () => {
		it("should say 'password incorrect'", () => {
			cy.visit("http://localhost:8080/login");

			cy.get("#username").type(username);
			cy.get("#password").type(authenticator.generate(notSecret));
			cy.get(":nth-child(3) > input").click();

			cy.get("#message").should("have.text", "password incorrect");
		});
	});

	describe("correct username and password", () => {
		it("should redirect to /list", () => {
			cy.visit("http://localhost:8080/login");

			cy.get("#username").type(username);
			cy.get("#password").type(authenticator.generate(secret));
			cy.get(":nth-child(3) > input").click();

			cy.location("pathname").should("include", "/list");
		});
	});
});
