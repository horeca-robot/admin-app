# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
ENV NODE_ENV production
RUN npm run build_prod
EXPOSE 8081
CMD ["npm", "run", "serve"]

# # production stage
# FROM nginx:stable-alpine as production-stage
# COPY default.conf /etc/nginx/conf.d/default.conf
# COPY --from=build-stage /app/dist /usr/share/nginx/html
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]