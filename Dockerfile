FROM clojure:tools-deps-1.11.1.1208 AS backend-build

WORKDIR /app

COPY package.json package-lock.json tailwind.config.js ./
COPY src/cljs ./src/cljs
COPY resources ./resources
COPY global.css ./global.css
COPY . .

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

WORKDIR /app

COPY deps.edn ./
COPY src/clj ./src/clj

RUN clj -T:build uber

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY /target/app.jar /app

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]
