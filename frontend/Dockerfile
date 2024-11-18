FROM node:18-alpine as frontend-builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY ../.. .
RUN npm run build

FROM nginx:alpine
COPY --from=frontend-builder /app/build /usr/share/nginx/html

# Expose the port
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]