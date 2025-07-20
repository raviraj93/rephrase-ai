# 🤖 Rephrase AI

**Rephrase AI** is a Spring Boot application that uses OpenAI’s GPT models to rephrase text in multiple styles. It supports rephrasing, humanizing tone, and playful rewriting by leveraging configurable prompts and modes.

---

## 🚀 Features

* Rephrases text using OpenAI GPT models
* Humanizes sentences with warm, empathetic tone
* Adds casual quirks or intentional errors for playfulness
* Easily switchable modes via query param (`?mode=playful`)
* Modular support for multiple LLM providers (OpenAI, Perplexity)
* Includes integration and unit tests

---

## 📆 Requirements

* Java 21+
* Gradle 7+
* OpenAI API Key

---

## ⚙️ Setup & Run

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/rephrase-ai.git
cd rephrase-ai
```

### Set environment variable (or use `.env` or IDE config)

```bash
export OPENAI_API_KEY=your_openai_key
```

### Run the application

```bash
./gradlew bootRun
```

The service will be running at: `http://localhost:8080/api/v1`

---

## 🔗 API Endpoints

### `POST /api/v1/rephrase`

#### Request

```json
{
  "text": "Can you make this sound more professional?"
}
```

#### Optional Query Param

* `mode=default` (default)
* `mode=humanize`
* `mode=playful`

#### Response

```json
{
  "rephrased": "Could you kindly enhance the professionalism of this sentence?"
}
```

---

## 📺 Example `application.properties`

```properties
openai.api.key=${OPENAI_API_KEY}
openai.api.url=https://api.openai.com/v1/chat/completions
openai.model=gpt-4

openai.prompt.default=You are a helpful assistant that rephrases text clearly.
openai.prompt.humanize=You are a friendly assistant who rewrites text in a warm, empathetic, and human tone with subtle emotions.
openai.prompt.playful=You are a quirky assistant who rephrases text with a friendly tone and adds a few spelling or verb mistakes playfully to make it sound more casual.

openai.mode.default=default
openai.mode.humanize=humanize
openai.mode.playful=playful

logging.level.com.rephraseai=DEBUG
logging.level.org.springframework.web.client.RestTemplate=DEBUG
```

---

## 📅 Testing

```bash
./gradlew test
```

Includes:

* Unit tests (LLM client, services)
* Integration tests (`@SpringBootTest`, `@MockMvc`)

---

## 📚 Project Structure

```
com.rephraseai
├── client/          # LLM clients (OpenAI etc.)
├── config/          # Config class (OpenAiProperties)
├── controller/      # REST endpoints
├── dto/             # Request/response objects
├── provider/        # Perplexity/OpenAI wrappers
├── service/         # Rephrase logic abstraction
└── RephraseAiApplication.java
```

---

## 🔐 Security

* API key securely read from environment/config
* Use `@Value` injection or bind via `@ConfigurationProperties`

---

## 🌟 Contributing

* Fork, branch, code, test, and PR
* Follow clean code and test-driven practices

---

## 🔼 License

MIT License. See `LICENSE` file.

---

## 🥇 Credits

* [OpenAI API](https://platform.openai.com/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Jackson](https://github.com/FasterXML/jackson)

> Built with ❤️ by developers, for developers.
