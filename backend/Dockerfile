FROM python:3.9-slim
WORKDIR /app
RUN echo "<h1>Вы видите это, потому что бэкендеры не написали Dockerfile!</h1>" > index.html
CMD ["python", "-m", "http.server", "8080"]
EXPOSE 8080