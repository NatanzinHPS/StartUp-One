# PRODUCTION BUILD
# FROM node:20 AS build
# WORKDIR /app
# COPY package*.json ./
# RUN npm install -g @angular/cli && npm install
# COPY . .
# RUN ng build --configuration production

# FROM nginx:alpine
# COPY --from=build /app/dist/Rodaki-Front/browser /usr/share/nginx/html
# RUN rm /etc/nginx/conf.d/default.conf
# COPY nginx.conf /etc/nginx/conf.d/default.conf
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]

# DEVELOPMENT BUILD
# Etapa de build
FROM node:20 AS build

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o package.json e o package-lock.json para o contêiner
COPY package*.json ./

# Instalar as dependências globais e locais
RUN npm install -g @angular/cli && npm install

# Copiar o restante do código da aplicação para dentro do contêiner
COPY . .

# Expor a porta que o Angular vai utilizar 
EXPOSE 80

# Rodar o comando de desenvolvimento do Angular com hot reload
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "80", "--poll", "2000"]