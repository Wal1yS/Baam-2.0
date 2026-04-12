FROM python:3.9-slim
WORKDIR /app
RUN echo "<h1>You see this because the backend developers didn't write a Dockerfile!</h1>" > index.html
CMD ["python", "-m", "http.server", "8080"]
EXPOSE 8080