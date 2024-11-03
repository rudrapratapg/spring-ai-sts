# Spring AI Integration Project

## Overview
This project demonstrates the integration of **Spring AI** with various artificial intelligence models, bringing cutting-edge AI capabilities to Java-based applications. Traditionally, accessing advanced AI models, such as OpenAI's ChatGPT, has been dominated by Python and JavaScript. However, Spring AI allows Java developers to streamline the inclusion of Generative AI into their projects without unnecessary complexity.

With this project, Java developers can easily interact with top AI models, perform prompt engineering, utilize retrieval-augmented generation (RAG), and work with advanced capabilities like image generation, text-to-speech, and speech-to-text.

## Features
This project includes the following features:
- **Integrations with Major AI Models**: Supports OpenAI, Azure OpenAI, Amazon Bedrock, Hugging Face, Ollama, Google Vertex AI (PaLM2 and Gemini), Mistral AI, Anthropic, and IBM’s WatsonxAI.
- **RAG (Retrieval-Augmented Generation)**: Leverage RAG to enrich model responses with additional context, providing more specialized and accurate results.
- **Image and Audio Capabilities**: Includes models for AI-driven image generation and audio processing, such as text-to-speech and speech-to-text transcription.
- **Customizable Prompts and Response Formatting**: Enable refined model outputs by tailoring prompts and response formats.
- **RESTful API Development**: Offers a streamlined REST API to interact with AI models, making it easy to integrate these capabilities into other applications.

## Getting Started

### Prerequisites
- **Java** (minimum version 17)
- **Spring Boot** (version 3.3.4 or higher)
- Familiarity with **RESTful APIs** is recommended.

### Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rudrapratapg/spring-ai-sts
   cd spring-ai-sts
   ```
2. **Install Dependencies**: Make sure to update any dependencies in the `pom.xml` if necessary.
3. **Configure API Keys**: Set up API keys for the models you plan to use (OpenAI, Azure, Amazon Bedrock, etc.) in your environment variables or application properties.

### Run the Application
To start the application:
```bash
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`.

## Usage

### Sample Endpoints
1. **Ask a Question to ChatGPT**:
   Send a POST request to `/api/chat` with a question, and receive the AI model's response.
2. **Generate an Image**:
   Use `/api/generate-image` to request AI-generated images based on your description.
3. **Text-to-Speech**:
   Convert text to audio using `/api/text-to-speech`.

For detailed usage examples, please refer to the [API Documentation](docs/API.md).

## Technologies Used
- **Java**
- **Spring Boot** - Framework for REST API development and dependency injection
- **Spring AI** - Facilitates seamless interaction with various AI models
- **OpenAI, Azure OpenAI, Amazon Bedrock, Google Vertex AI, Hugging Face** - AI model providers
- **Vector Databases** - Supports advanced RAG workflows

## Project Highlights

1. **Comprehensive AI Model Support**: Easily switch between different AI providers and models to suit diverse application needs.
2. **Prompt Engineering and Fine-tuning**: Improve response quality by adjusting prompts and responses to meet specific application requirements.
3. **End-to-End API**: Create and test REST APIs for various AI interactions, making the project versatile and extensible.

## Roadmap
This project may include future integrations with additional AI models and expanded support for more vector databases, enhancing its Retrieval Augmented Generation (RAG) capabilities.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments
This project is inspired by the advancements in AI and aims to bridge the gap between AI capabilities and Java applications by leveraging the Spring AI framework.

