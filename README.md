# 🧠 ReddInterview — Real-Time Interview Intelligence from Reddit

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://github.com/your-github-username/ReddInterview/graphs/commit-activity)
![Java 21](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![Microservices](https://img.shields.io/badge/Microservices-Architecture-blueviolet.svg)

ReddInterview is a distributed microservices-based application designed to provide real-time intelligence on interview-related discussions happening on Reddit. By streaming posts from targeted subreddits like r/interviews, r/leetcode, and r/big_tech_interviews, ReddInterview aims to extract valuable insights for job seekers, recruiters, and anyone interested in the interview process.

## 🧩 Key Insights Extracted

ReddInterview focuses on identifying and extracting the following information from Reddit posts:

* **LeetCode questions:** Discover commonly discussed coding challenges.
* **Company mentions:** Track which companies are frequently mentioned in interview contexts.
* **Interview outcomes (Pass/Fail):** Analyze success and failure stories.
* **Candidate answers and experiences:** Learn from the real experiences of other candidates.
* **Tech Stack:** Identify the technologies being discussed in relation to interviews.
* **Tech Role:** Understand the specific roles candidates are interviewing for.

## 🚀 Tech Stack

The project leverages a modern microservices architecture built with the following technologies:

* **Primary language:** Java 21
* **Core Framework:** Spring Boot
* **Architecture:** Microservices
* **Message Broker:** Spring Kafka (Kafka producer for post streaming)
* **Reddit API Integration:** Spring WebFlux + WebClient, OAuth2-secured
* **Scheduling:** Spring Scheduling (Periodic subreddit polling)
* **Messaging Backbone:** Kafka
* **Real-time Notifications (Planned):** Redis (Pub/Sub)
* **Security (Planned):** Spring Security (Auth microservice with JWT)
* **Data Storage (Planned):** PostgreSQL / MongoDB (Interview data storage)
* **API Gateway (Planned):** Zuul
* **Containerization (Planned):** Docker

## 🎯 Project Goals

The primary goals of ReddInterview are:

* ✅ **Build a Kafka-based Reddit post producer using Spring Boot:** Successfully implemented.
* 🔄 **Stream interview-related content from multiple subreddits:** Successfully implemented for specified subreddits.
* 📊 **Extract structured data: questions, companies, answers:** In progress (Next Step: Kafka Consumer Service).
* 🧠 **Add regex/NLP-based parsing to classify content:** Planned for the Kafka Consumer Service.
* 💾 **Store structured results in a database:** Planned for the Interview Service.
* 🔐 **Secure endpoints using JWT-based Spring Security:** Planned for the Auth Service.
* 📢 **Send live notifications via Redis Pub/Sub:** Planned for the Notification Service.
* 🌐 **Route traffic via Zuul API Gateway:** Planned for the API Gateway Service.
* 📦 **Containerize services using Docker:** Planned for deployment.
* ☁️ **Deploy to the cloud (EC2, Railway, or Fly.io):** Planned for deployment.

## ✅ Current Progress

The following components are currently under development or completed:

* **Spring Boot Kafka Producer Service:** ✅ Created and functional.
* **OAuth2 Reddit API Integration (Client Credentials flow):** ✅ Implemented for secure communication with the Reddit API.
* **Multi-subreddit support (e.g., interviews, leetcode, big\_tech\_interviews):** ✅ Configured to fetch data from multiple targeted subreddits.
* **Fetched post metadata and full content using `selftext`:** ✅ Successfully retrieving relevant post information.
* **Publishing structured `RedditPost` objects to Kafka topic `reddit.raw.posts`:** ✅ Raw Reddit post data is being streamed to Kafka.
* **Kafka Consumer Service:** 🚧 **Next Step:** This service will consume raw posts from Kafka and begin the data extraction process.
* **InterviewService with DB persistence:** ⏳ Planned for development after the Consumer Service.
* **Zuul gateway and Redis notification:** ⏳ Planned for later stages of development.
* **CI/CD + Dockerization:** ⏳ Planned for deployment and continuous integration.

## 📂 Project Structure (Planned)

The project's codebase is planned to be organized under the `reddinterview/` directory, containing the following microservices:

* `reddinterview/reddit-producer-service/`: Responsible for fetching and publishing raw Reddit posts to Kafka.
* `reddinterview/reddit-consumer-service/`: Consumes raw posts, extracts insights, and stores structured data.
* `reddinterview/interview-service/`: Manages the storage and retrieval of structured interview data.
* `reddinterview/auth-service/`: Handles user authentication and authorization (JWT).
* `reddinterview/notification-service/`: Manages real-time notifications via Redis Pub/Sub.
* `reddinterview/api-gateway-service/`: Provides a single entry point for accessing the application's APIs.

Feel free to fork the repository and submit a pull request with your proposed changes. We are also open to discussing collaboration ideas and new features. Let's build this valuable resource together!
