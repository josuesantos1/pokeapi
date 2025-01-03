FROM clojure:tools-deps-1.11.1.1208 AS build

WORKDIR /app

ADD . /app

RUN apt-get update && apt-get install -y \
  curl \
  gnupg2 \
  lsb-release \
  ca-certificates \
  && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://deb.nodesource.com/setup_16.x | bash - && \
    apt-get install -y nodejs

RUN npm install

RUN npx tailwindcss -i global.css -o resources/public/assets/css/output.css --minify

RUN npx shadow-cljs release app

RUN clj -T:build uber

WORKDIR /app

FROM openjdk:17-jdk-slim

COPY resources ./resources
COPY --from=build /app/target/app.jar app.jar

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]
