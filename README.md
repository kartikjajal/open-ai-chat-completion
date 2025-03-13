# OpenAI Prompt Client Application

## Overview

This is an OpenAI Client Application that interacts with OpenAI's API to generate responses for user inputs (prompts). The application is built using Spring Boot and Maven, allowing you to easily deploy and run the service.

The application provides an endpoint `/chat` to send user prompts and get generated responses from OpenAI. The `OPEN_AI_KEY` environment variable is required to authenticate your requests to OpenAI.

---

## Features
- Accepts user input via a query parameter (`input`) and returns AI-generated responses.
- Spring Boot-based RESTful service.
- Easy configuration and deployment.

---

## Requirements

- Java JDK 8 or later.
- Maven.
- Internet connection for OpenAI API access.

---

## Configuration

1. **API Key**:  
   Set up the `OPEN_AI_KEY` environment variable with your OpenAI API key.

   Example for Linux/macOS:
   ```bash
   export OPEN_AI_KEY=your_openai_api_key

## How to Run the Application
   ```bash
   mvn spring-boot:run -DOPEN_AI_KEY=your_openai_api_key