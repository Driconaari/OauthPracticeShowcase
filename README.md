# Custom Java OAuth2 and JWT Implementation

A zero-dependency authentication system built using native Java libraries. This project serves as a functional demonstration of JWT-based security, handling everything from manual JSON parsing to cryptographic signature verification without the use of high-level frameworks.

## Technical Features

* **Stateless Security**: Implements JSON Web Tokens (JWT) to manage user authentication, eliminating the need for server-side session state.
* **Manual Request Dispatching**: Uses a centralized routing pattern in `Main.java` to map HTTP contexts to dedicated handler classes.
* **Bearer Token Validation**: A `DataHandler` class that extracts the `Authorization` header and validates the cryptographic integrity of the token via a service layer.
* **Vanilla JavaScript Integration**: A frontend implementation using the Fetch API and `localStorage` to handle the token lifecycle on the client side.

## System Components

### Backend Architecture

* **`Main.java`**: Acts as the system dispatcher and entry point. It initializes the `HttpServer` and registers API endpoints and static resource paths.
* **`AuthHandler.java`**: Manages the login process. It extracts credentials from the raw request body and issues a signed JWT upon successful validation.
* **`DataHandler.java`**: A protected resource controller. It intercepts requests to restricted paths and denies access if a valid token is not present.
* **`TokenProvider.java`**: The service layer responsible for the high-level logic of issuing and validating tokens.
* **`JwtUtils.java`**: The core utility for Base64 encoding and HMAC-SHA256 signature generation.

### Frontend Logic

1. **Authentication**: The user submits credentials which are sent as a POST request to `/login`.
2. **Storage**: On a 200 OK response, the resulting `access_token` is stored in the browser's `localStorage`.
3. **Authorization**: For protected requests, the token is retrieved and injected into the request headers as a `Bearer` token.
4. **Access**: The server verifies the token and returns the requested resource or a 401 Unauthorized status.

## Configuration and Usage

### Compilation

To compile the project from the root directory:

```bash
javac -d bin src/com/authproject/**/*.java

```

### Execution

To start the server:

```bash
java -cp bin com.authproject.Main

```

The server will default to listening on `http://localhost:8080`.

## Implementation Details

The project utilizes manual string manipulation for JSON parsing to demonstrate the structure of HTTP payloads. Consequently, the `AuthHandler` expects a specific JSON format without excessive whitespace or unconventional formatting. The `DataHandler` uses byte-length calculations for response headers to ensure cross-platform compatibility with different character encodings.
